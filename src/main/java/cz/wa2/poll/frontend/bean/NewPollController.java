package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.AnswerHelper;
import cz.wa2.poll.frontend.dto.PollDTO;
import cz.wa2.poll.frontend.exception.ClientException;
import cz.wa2.poll.frontend.rest.VoterGroupClient;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "newPoll")
@ViewScoped
public class NewPollController extends UniversalController implements Serializable {

    @ManagedProperty(value="#{voter}")
    LoggedVoter loggedVoter;

    private String name;
    private String question;
    private String answer;
    private List<String> answers;

    @PostConstruct
    public void init() {
        answer = "";
        answers = new ArrayList<String>();

    }

    public String createPoll(){
        PollDTO pollDTO = new PollDTO();
        pollDTO.setName(name);
        pollDTO.setQuestion(question);
        VoterGroupClient voterGroupClient = new VoterGroupClient();
        try {
            voterGroupClient.createPoll(pollDTO, loggedVoter.getVoterGroupDTO().getId());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return "/supervised-groups.xhtml";
    }

    public LoggedVoter getLoggedVoter() {
        return loggedVoter;
    }

    public void setLoggedVoter(LoggedVoter loggedVoter) {
        this.loggedVoter = loggedVoter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void createNew() {
        if(answers.contains(answer)) {
            System.out.println("Už je uloženo");
        }
        else {
            answers.add(answer);
            answer = "";
        }
    }

    public String reinit() {
        answer = "";

        return null;
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
}
