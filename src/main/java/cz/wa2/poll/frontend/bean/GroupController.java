package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.VoterGroupDTO;
import cz.wa2.poll.frontend.exception.ClientException;
import cz.wa2.poll.frontend.rest.VoterClient;
import cz.wa2.poll.frontend.rest.VoterGroupClient;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "createGroup")
@RequestScoped
public class GroupController extends UniversalController {

    @ManagedProperty(value = "#{voter}")
    LoggedVoter loggedVoter;

    private String name;
    private String description;

    public String createGroup() {
        VoterGroupDTO voterGroupDTO = new VoterGroupDTO();
        voterGroupDTO.setName(name);
        voterGroupDTO.setDescription(description);
        VoterClient voterGroupClient = new VoterClient();
        try {
            voterGroupClient.saveVoterGroup(loggedVoter.getVoter().getId(), voterGroupDTO);
            RequestContext.getCurrentInstance().update("growl");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Skupina byla úspěšně vytvořena"));
            return "success";
        } catch (ClientException e) {
            RequestContext.getCurrentInstance().update("growl");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            return "";
        }
    }

    public String updateSelectedGroup(VoterGroupDTO voterGroupDTO) {
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
