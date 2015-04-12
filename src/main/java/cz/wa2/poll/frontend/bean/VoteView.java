package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.AnswerHelper;
import cz.wa2.poll.frontend.dto.BallotDTO;
import cz.wa2.poll.frontend.rest.BallotClient;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "vote")
@RequestScoped
public class VoteView extends UniversalController implements Serializable {

    @ManagedProperty(value="#{voter}")
    LoggedVoter loggedVoter;

    @PostConstruct
    public void init() {
        id = loggedVoter.getBallotDTO().getId();
        name = loggedVoter.getPollDTO().getName();
        question = loggedVoter.getPollDTO().getQuestion();
        answerHelpers = loggedVoter.getPollDTO().getAnswers();
        answers = new ArrayList<String>();
        for(AnswerHelper helper: answerHelpers){
            answers.add(helper.getAnswer());
        }
    }

    private Long id;
    private String question;
    private String name;
    private AnswerHelper answerHelper;
    private List<AnswerHelper> answerHelpers;
    private String answer;
    private List<String> answers;


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

    public AnswerHelper getAnswerHelper() {
        return answerHelper;
    }

    public void setAnswerHelper(AnswerHelper answerHelper) {
        this.answerHelper = answerHelper;
    }

    public List<AnswerHelper> getAnswerHelpers() {
        return answerHelpers;
    }

    public void setAnswerHelpers(List<AnswerHelper> answerHelpers) {
        this.answerHelpers = answerHelpers;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String vote(){
        BallotDTO ballotDTO = new BallotDTO();
        ballotDTO.setId(id);
        for(AnswerHelper helper: answerHelpers){
            if(helper.getAnswer().equals(answer)){
                ballotDTO.setAnswer(helper.getId());
                break;
            }
        }

        BallotClient ballotClient = new BallotClient();
        ballotClient.updateBallot(ballotDTO);
        return "/unvoted-polls.xhtml";
    }
}
