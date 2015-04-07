package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.PollDTO;
import cz.wa2.poll.frontend.exception.ClientException;
import cz.wa2.poll.frontend.rest.VoterGroupClient;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "newPoll")
@RequestScoped
public class NewPollController {

    @ManagedProperty(value="#{voter}")
    LoggedVoter loggedVoter;

    private String name;
    private String question;

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
}
