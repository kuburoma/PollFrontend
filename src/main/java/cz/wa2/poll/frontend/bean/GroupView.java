package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.VoterGroupDTO;
import cz.wa2.poll.frontend.exception.ClientException;
import cz.wa2.poll.frontend.rest.VoterClient;
import cz.wa2.poll.frontend.rest.VoterGroupClient;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name="groupView")
@ViewScoped
public class GroupView extends UniversalController implements Serializable {

    private List<VoterGroupDTO> voterGroupDTOs;

    @ManagedProperty(value="#{voter}")
    LoggedVoter loggedVoter;
    VoterClient voterClient = new VoterClient();

    @PostConstruct
    public void init() {
        String path = ((HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest()).getRequestURI();
        if(path.equals("/supervised-groups.xhtml")){
            voterGroupDTOs = voterClient.getSupervisedGroups(loggedVoter.getVoter().getId());
        }

        VoterClient voterClient = new VoterClient();
        voterGroupDTOs = loggedVoter.getVoterGroupDTOList();
    }

    public String updateSelectedGroup(VoterGroupDTO voterGroupDTO){
        loggedVoter.setVoterGroupDTO(voterGroupDTO);
        return "/group-voters.xhtml";
    }

    public String createNewPoll(VoterGroupDTO voterGroupDTO){
        loggedVoter.setVoterGroupDTO(voterGroupDTO);
        return "/new-poll.xhtml";
    }

    public String unregisterFromGroup(VoterGroupDTO voterGroupDTO){
        VoterGroupClient voterGroupClient = new VoterGroupClient();
        try {
            voterGroupClient.deleteVoterFromVotergroup(voterGroupDTO.getId(), loggedVoter.getVoter().getId());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return "/unregister-group.xhtml";
    }

    public String registerIntoGroup(VoterGroupDTO voterGroupDTO){
        VoterGroupClient voterGroupClient = new VoterGroupClient();
        try {
            voterGroupClient.putVoterToVotergroup(voterGroupDTO.getId(), loggedVoter.getVoter().getId());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return "/register-group.xhtml";
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
