package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.BallotDTO;
import cz.wa2.poll.frontend.rest.BallotClient;
import cz.wa2.poll.frontend.rest.PollClient;
import cz.wa2.poll.frontend.rest.VoterClient;
import org.primefaces.model.chart.PieChartModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.Iterator;
import java.util.List;

@ManagedBean(name = "voteChart")
@ViewScoped
public class VoteChartView {

    @ManagedProperty(value="#{voter}")
    LoggedVoter loggedVoter;

    private PollClient pollClient;
    private PieChartModel chart = new PieChartModel();

    @PostConstruct
    public void init() {

        pollClient = new PollClient();
        List<BallotDTO> ballotList = pollClient.getPollBallots(loggedVoter.getPollDTO().getId());
        int notVoted = 0;
        int yes = 0;
        int no = 0;
        Iterator<BallotDTO> it = ballotList.iterator();
        while(it.hasNext()){
            Long answer = it.next().getAnswer();
            if(answer == 0L){
                notVoted++;
            }
            if(answer == 1L){
                yes++;
            }
            if(answer == 2L){
                no++;
            }
        }


        chart = new PieChartModel();
        chart.getData().put("Not Voted", notVoted);
        chart.getData().put("Yes", yes);
        chart.getData().put("No", no);

    }

    public LoggedVoter getLoggedVoter() {
        return loggedVoter;
    }

    public void setLoggedVoter(LoggedVoter loggedVoter) {
        this.loggedVoter = loggedVoter;
    }

    public PollClient getPollClient() {
        return pollClient;
    }

    public void setPollClient(PollClient pollClient) {
        this.pollClient = pollClient;
    }

    public PieChartModel getChart() {
        return chart;
    }

    public void setChart(PieChartModel chart) {
        this.chart = chart;
    }
}
