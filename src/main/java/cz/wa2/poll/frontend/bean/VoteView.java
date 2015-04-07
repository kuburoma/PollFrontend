package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.BallotDTO;
import cz.wa2.poll.frontend.rest.BallotClient;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "vote")
@RequestScoped
public class VoteView {

    @ManagedProperty(value="#{voter}")
    LoggedVoter loggedVoter;

    @PostConstruct
    public void init() {
        id = loggedVoter.getBallotDTO().getId();
        name = loggedVoter.getPollDTO().getName();
        question = loggedVoter.getPollDTO().getQuestion();
    }

    private Long id;
    private String answer;
    private String question;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LoggedVoter getLoggedVoter() {
        return loggedVoter;
    }

    public void setLoggedVoter(LoggedVoter loggedVoter) {
        this.loggedVoter = loggedVoter;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String vote(){
        BallotDTO ballotDTO = new BallotDTO();
        ballotDTO.setId(id);
        ballotDTO.setAnswer(Long.valueOf(answer));
        BallotClient ballotClient = new BallotClient();
        ballotClient.updateBallot(ballotDTO);
        return "/unvoted-poll.xhtml";
    }
}
