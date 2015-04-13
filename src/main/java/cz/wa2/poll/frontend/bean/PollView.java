package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.PollDTO;
import cz.wa2.poll.frontend.dto.VoterGroupDTO;
import cz.wa2.poll.frontend.exception.ClientException;
import cz.wa2.poll.frontend.rest.PollClient;
import cz.wa2.poll.frontend.rest.VoterClient;
import cz.wa2.poll.frontend.rest.VoterGroupClient;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name="pollView")
@ViewScoped
public class PollView extends UniversalController implements Serializable {


    private VoterClient voterClient;
    private PollClient pollClient;
    private List<PollDTO> pollDTOs;

    @ManagedProperty(value="#{voter}")
    LoggedVoter loggedVoter;

    @PostConstruct
    public void init() {
        voterClient = new VoterClient(loggedVoter.getRestServerAddress());
        pollClient = new PollClient(loggedVoter.getRestServerAddress());

        String path = ((HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest()).getRequestURI();
        if(path.equals("/unvoted-polls.xhtml")){
            pollDTOs = voterClient.getNonvotedPolls(loggedVoter.getVoter().getId());
        }
        if(path.equals("/voted-polls.xhtml")){
            pollDTOs = voterClient.getVotedPolls(loggedVoter.getVoter().getId());
        }
        if(path.equals("/supervised-polls.xhtml")){
            pollDTOs = voterClient.getSupervisedPolls(loggedVoter.getVoter().getId());
        }
    }

    public String vote(PollDTO pollDTO){
        loggedVoter.setPollDTO(pollDTO);
        loggedVoter.setBallotDTO(voterClient.getBallot(loggedVoter.getVoter().getId(), pollDTO.getId()));
        return "success";
    }

    public String results(PollDTO pollDTO){
        loggedVoter.setPollDTO(pollDTO);
        return "success";
    }

    public String deletePoll(PollDTO pollDTO){
        try {
            pollClient.deletePoll(pollDTO.getId());
        } catch (ClientException e) {
            addMessage(FacesMessage.SEVERITY_ERROR,"Error",e.getMessage());
            return null;
        }
        return "success";
    }

    public List<PollDTO> getPollDTOs() {
        return pollDTOs;
    }

    public LoggedVoter getLoggedVoter() {
        return loggedVoter;
    }

    public void setLoggedVoter(LoggedVoter loggedVoter) {
        this.loggedVoter = loggedVoter;
    }
}
