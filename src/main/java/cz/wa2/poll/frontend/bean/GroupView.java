package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.VoterGroupDTO;
import cz.wa2.poll.frontend.exception.ClientException;
import cz.wa2.poll.frontend.rest.VoterClient;
import cz.wa2.poll.frontend.rest.VoterGroupClient;
import org.glassfish.jersey.process.internal.RequestScoped;
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
@RequestScoped
public class GroupView extends UniversalController implements Serializable {

    private List<VoterGroupDTO> voterGroupDTOs;

    @ManagedProperty(value="#{voter}")
    LoggedVoter loggedVoter;
    VoterClient voterClient;
    VoterGroupClient voterGroupClient;

    @PostConstruct
    public void init() {
        voterClient = new VoterClient(loggedVoter.getRestServerAddress());
        voterGroupClient = new VoterGroupClient(loggedVoter.getRestServerAddress());

        String path = ((HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest()).getRequestURI();
        if(path.equals("/supervised-groups.xhtml")){
            System.out.println("supervised");
            voterGroupDTOs = voterClient.getSupervisedGroups(loggedVoter.getVoter().getId());
        }
        if(path.equals("/unregister-group.xhtml")){
            voterGroupDTOs = voterClient.getRegistredGroups(loggedVoter.getVoter().getId());
            System.out.println("unregister");
        }
        if(path.equals("/register-group.xhtml")){
            voterGroupDTOs = voterClient.getNotregistredGroups(loggedVoter.getVoter().getId());
            System.out.println("register");
        }
    }

    public String updateSelectedGroup(VoterGroupDTO voterGroupDTO){
        loggedVoter.setVoterGroupDTO(voterGroupDTO);
        return "success";
    }

    public String createNewPoll(VoterGroupDTO voterGroupDTO){
        loggedVoter.setVoterGroupDTO(voterGroupDTO);
        return "success";
    }

    public String unregisterFromGroup(VoterGroupDTO voterGroupDTO){
        try {
            voterGroupClient.deleteVoterFromVotergroup(voterGroupDTO.getId(), loggedVoter.getVoter().getId());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return "success";
    }

    public String registerIntoGroup(VoterGroupDTO voterGroupDTO){
        try {
            voterGroupClient.putVoterToVotergroup(voterGroupDTO.getId(), loggedVoter.getVoter().getId());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return "success";
    }

    public String deleteVoterGroup(VoterGroupDTO voterGroupDTO){
        try {
            voterGroupClient.deleteVoterGroup(voterGroupDTO.getId());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return "success";
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
