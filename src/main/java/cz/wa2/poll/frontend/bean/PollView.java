package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.PollDTO;
import cz.wa2.poll.frontend.dto.VoterGroupDTO;
import cz.wa2.poll.frontend.exception.ClientException;
import cz.wa2.poll.frontend.rest.VoterClient;
import cz.wa2.poll.frontend.rest.VoterGroupClient;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean(name="pollView")
@ViewScoped
public class PollView {


    private VoterClient voterClient = new VoterClient();
    private List<PollDTO> pollDTOs;

    @ManagedProperty(value="#{voter}")
    LoggedVoter loggedVoter;

    @PostConstruct
    public void init() {
        pollDTOs = loggedVoter.getPollDTOList();
    }

    public String vote(PollDTO pollDTO){
        loggedVoter.setPollDTO(pollDTO);
        loggedVoter.setBallotDTO(voterClient.getBallot(loggedVoter.getVoter().getId(), pollDTO.getId()));
        return "/vote-in-poll.xhtml";
    }

    public String results(PollDTO pollDTO){
        loggedVoter.setPollDTO(pollDTO);
        return "/vote-chart.xhtml";
    }

    public String nonvotedPolls(VoterGroupDTO voterGroupDTO){
        VoterGroupClient voterGroupClient = new VoterGroupClient();
        try {
            voterGroupClient.putVoterToVotergroup(voterGroupDTO.getId(), loggedVoter.getVoter().getId());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return "/register-group.xhtml";
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
