package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.VoterDTO;
import cz.wa2.poll.frontend.dto.VoterGroupDTO;
import cz.wa2.poll.frontend.exception.ClientException;
import cz.wa2.poll.frontend.rest.VoterGroupClient;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name="groupVotersView")
@RequestScoped
public class GroupVotersView extends UniversalController implements Serializable {

    private List<VoterDTO> voterDTOs;
    private VoterGroupClient voterGroupClient = new VoterGroupClient();
    private Long votergroupId;
    private VoterGroupDTO voterGroupDTO;

    @ManagedProperty(value="#{voter}")
    LoggedVoter loggedVoter;

    @PostConstruct
    public void init() {
        try {
            votergroupId = loggedVoter.getVoterGroupDTO().getId();
            voterDTOs = voterGroupClient.getVoters(votergroupId);
            voterGroupDTO = loggedVoter.getVoterGroupDTO();
        } catch (ClientException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public String updateSelectedGroup(VoterGroupDTO voterGroupDTO){
        loggedVoter.setVoterGroupDTO(voterGroupDTO);
        return "/group-voters.xhtml";
    }

    public String removeUser(Long idVoter){
        try {
            voterGroupClient.deleteVoterFromVotergroup(votergroupId,idVoter);
        } catch (ClientException e) {
            addMessage(FacesMessage.SEVERITY_ERROR,"Error", e.getMessage());
        }
        return "success";
    }

    public List<VoterDTO> getVoterDTOs() {
        return voterDTOs;
    }

    public LoggedVoter getLoggedVoter() {
        return loggedVoter;
    }

    public void setLoggedVoter(LoggedVoter loggedVoter) {
        this.loggedVoter = loggedVoter;
    }

    public void setVoterDTOs(List<VoterDTO> voterDTOs) {
        this.voterDTOs = voterDTOs;
    }

    public VoterGroupDTO getVoterGroupDTO() {
        return voterGroupDTO;
    }

    public void setVoterGroupDTO(VoterGroupDTO voterGroupDTO) {
        this.voterGroupDTO = voterGroupDTO;
    }
}
