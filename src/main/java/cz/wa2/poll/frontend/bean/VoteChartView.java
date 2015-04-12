package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.AnswerHelper;
import cz.wa2.poll.frontend.dto.BallotDTO;
import cz.wa2.poll.frontend.dto.PollDTO;
import cz.wa2.poll.frontend.rest.PollClient;
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

    @ManagedProperty(value = "#{voter}")
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
        List<AnswerHelper> answerHelpers = loggedVoter.getPollDTO().getAnswers();
        int[] votes = new int[answerHelpers.size()+1];
        Iterator<BallotDTO> it = ballotList.iterator();

        while (it.hasNext()) {
            Integer answer = it.next().getAnswer();
            if(answer == null){
                votes[votes.length-1] = votes[votes.length-1]+1;
            }
            votes[answer] = votes[answer]+1;
        }


        chart = new PieChartModel();
        for(int i = 0; i < votes.length -1; i++){
            for (AnswerHelper answerHelper: answerHelpers){
                if(i == answerHelper.getId()){
                    chart.getData().put(answerHelper.getAnswer(), votes[i]);
                    break;
                }
            }


        }

        chart.getData().put("Not Voted", votes[votes.length-1]);

        chart.setTitle("Votes");
        chart.setLegendPosition("e");
        chart.setFill(false);
        chart.setShowDataLabels(true);
        chart.setDiameter(150);

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
