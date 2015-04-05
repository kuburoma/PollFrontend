package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.VoterGroupDTO;
import cz.wa2.poll.frontend.exception.ClientException;
import cz.wa2.poll.frontend.rest.VoterClient;
import cz.wa2.poll.frontend.rest.VoterGroupClient;
import org.glassfish.jersey.process.internal.RequestScoped;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean(name="registerGroupView")
@RequestScoped
public class RegisterGroupView {

    private List<VoterGroupDTO> voterGroupDTOs;

    @ManagedProperty(value="#{voter}")
    LoggedVoter loggedVoter;

    @PostConstruct
    public void init() {
        VoterClient voterClient = new VoterClient();
        voterGroupDTOs = voterClient.getNotregistredGroups(loggedVoter.getVoter().getId());
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
