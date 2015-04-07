package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.BallotDTO;
import cz.wa2.poll.frontend.dto.PollDTO;
import cz.wa2.poll.frontend.dto.VoterDTO;
import cz.wa2.poll.frontend.dto.VoterGroupDTO;
import cz.wa2.poll.frontend.websocket.QueueConsumer;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.util.List;

@ManagedBean(name = "voter")
@SessionScoped
public class LoggedVoter {

    private VoterDTO voter;
    private VoterGroupDTO voterGroupDTO;
    private PollDTO pollDTO;
    private BallotDTO ballotDTO;
    private List<VoterGroupDTO> voterGroupDTOList;
    private List<PollDTO> pollDTOList;
    private QueueConsumer queueConsumer;

    public QueueConsumer getQueueConsumer() {
        return queueConsumer;
    }

    public void setQueueConsumer(QueueConsumer queueConsumer) {
        this.queueConsumer = queueConsumer;
    }

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

    public List<VoterGroupDTO> getVoterGroupDTOList() {
        return voterGroupDTOList;
    }

    public void setVoterGroupDTOList(List<VoterGroupDTO> voterGroupDTOList) {
        this.voterGroupDTOList = voterGroupDTOList;
    }

    public List<PollDTO> getPollDTOList() {
        return pollDTOList;
    }

    public void setPollDTOList(List<PollDTO> pollDTOList) {
        this.pollDTOList = pollDTOList;
    }

    public PollDTO getPollDTO() {
        return pollDTO;
    }

    public void setPollDTO(PollDTO pollDTO) {
        this.pollDTO = pollDTO;
    }

    public BallotDTO getBallotDTO() {
        return ballotDTO;
    }

    public void setBallotDTO(BallotDTO ballotDTO) {
        this.ballotDTO = ballotDTO;
    }
}
