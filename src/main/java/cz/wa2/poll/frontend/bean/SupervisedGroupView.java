package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.VoterGroupDTO;
import cz.wa2.poll.frontend.rest.VoterClient;
import cz.wa2.poll.frontend.rest.VoterGroupClient;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean(name="supervisedGroupView")
@ViewScoped
public class SupervisedGroupView {

    private List<VoterGroupDTO> voterGroupDTOs;

    @ManagedProperty(value="#{voter}")
    LoggedVoter loggedVoter;

    @PostConstruct
    public void init() {
        VoterClient voterClient = new VoterClient();
        voterGroupDTOs = voterClient.getSupervisedGroups(loggedVoter.getVoter().getId());
    }

    public String updateSelectedGroup(VoterGroupDTO voterGroupDTO){
        loggedVoter.setVoterGroupDTO(voterGroupDTO);
        return "/group-voters.xhtml";
    }

    public List<VoterGroupDTO> getVoterGroupDTOs() {
        return voterGroupDTOs;
    }

    public LoggedVoter getLoggedVoter() {
        return loggedVoter;
    }

    public void setLoggedVoter(LoggedVoter loggedVoter) {
        this.loggedVoter = loggedVoter;
    }
}
