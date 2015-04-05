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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;

@ManagedBean(name="groupVotersView")
@RequestScoped
public class GroupVotersView {

    private List<VoterDTO> voterDTOs;
    private VoterGroupClient voterGroupClient = new VoterGroupClient();
    private Long votergroupId;

    @ManagedProperty(value="#{voter}")
    LoggedVoter loggedVoter;

    @PostConstruct
    public void init() {
        try {
            votergroupId = loggedVoter.getVoterGroupDTO().getId();
            voterDTOs = voterGroupClient.getVoters(votergroupId);
        } catch (ClientException e) {
            RequestContext.getCurrentInstance().update("growl");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
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
            RequestContext.getCurrentInstance().update("growl");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
        return "/group-voters.xhtml";
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
}
