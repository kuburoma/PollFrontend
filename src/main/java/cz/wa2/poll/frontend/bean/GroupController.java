package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.VoterGroupDTO;
import cz.wa2.poll.frontend.rest.VoterGroupClient;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "createGroup")
@RequestScoped
public class GroupController {

    @ManagedProperty(value="#{voter}")
    LoggedVoter loggedVoter;

    private String name;
    private String description;

    public void createGroup(){
        VoterGroupDTO voterGroupDTO = new VoterGroupDTO();
        voterGroupDTO.setName(name);
        voterGroupDTO.setDescription(description);
        voterGroupDTO.setSupervisor(loggedVoter.getVoter());
        VoterGroupClient voterGroupClient = new VoterGroupClient();
        voterGroupClient.create(voterGroupDTO);
    }

    public String updateSelectedGroup(VoterGroupDTO voterGroupDTO){
        loggedVoter.setVoterGroupDTO(voterGroupDTO);
        return "/group-voters.xhtml";
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
