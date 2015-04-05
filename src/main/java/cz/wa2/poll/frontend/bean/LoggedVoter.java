package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.VoterDTO;
import cz.wa2.poll.frontend.dto.VoterGroupDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "voter")
@SessionScoped
public class LoggedVoter {

    private VoterDTO voter;
    private VoterGroupDTO voterGroupDTO;

    public VoterDTO getVoter() {
        return voter;
    }

    public void setVoter(VoterDTO voter) {
        this.voter = voter;
    }

    public VoterGroupDTO getVoterGroupDTO() {
        return voterGroupDTO;
    }

    public void setVoterGroupDTO(VoterGroupDTO voterGroupDTO) {
        this.voterGroupDTO = voterGroupDTO;
    }
}
