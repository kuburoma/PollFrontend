package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.VoterGroupDTO;
import cz.wa2.poll.frontend.exception.ClientException;
import cz.wa2.poll.frontend.rest.VoterClient;
import cz.wa2.poll.frontend.rest.VoterGroupClient;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "manageGroup")
@RequestScoped
public class GroupController extends UniversalController {

    @ManagedProperty(value = "#{voter}")
    LoggedVoter loggedVoter;

    VoterClient voterClient;
    VoterGroupClient voterGroupClient;

    private String name;
    private String description;

    @PostConstruct
    public void init() {
        voterClient = new VoterClient(loggedVoter.getRestServerAddress());
        voterGroupClient = new VoterGroupClient(loggedVoter.getRestServerAddress());

        String path = ((HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest()).getRequestURI();
        if(path.equals("/group-voters.xhtml")){
            name = loggedVoter.getVoterGroupDTO().getName();
            description = loggedVoter.getVoterGroupDTO().getDescription();
        }
    }

    public String createGroup() {
        voterClient = new VoterClient(loggedVoter.getRestServerAddress());
        VoterGroupDTO voterGroupDTO = new VoterGroupDTO();
        voterGroupDTO.setName(name);
        voterGroupDTO.setDescription(description);
        try {
            voterClient.saveVoterGroup(loggedVoter.getVoter().getId(), voterGroupDTO);
            addMessage(FacesMessage.SEVERITY_INFO, "Info", "Skupina byla úspěšně vytvořena");
            return "success";
        } catch (ClientException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            return "";
        }
    }

    public String updateGroup() {
        voterGroupClient = new VoterGroupClient(loggedVoter.getRestServerAddress());
        VoterGroupDTO voterGroupDTO = new VoterGroupDTO();
        voterGroupDTO.setName(name);
        voterGroupDTO.setDescription(description);
        voterGroupDTO.setId(loggedVoter.getVoterGroupDTO().getId());
        try {
            voterGroupClient.updateVoterGroup(voterGroupDTO, loggedVoter.getVoterGroupDTO().getId());
            loggedVoter.setVoterGroupDTO(voterGroupDTO);
            addMessage(FacesMessage.SEVERITY_INFO, "Info", "Skupina byla úspěšně upravena");
            return null;
        } catch (ClientException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            return null;
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
