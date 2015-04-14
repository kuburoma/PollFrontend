package cz.wa2.poll.frontend.rest;

import cz.wa2.poll.frontend.dto.BallotDTO;
import cz.wa2.poll.frontend.dto.PollDTO;
import cz.wa2.poll.frontend.dto.VoterDTO;
import cz.wa2.poll.frontend.dto.VoterGroupDTO;
import cz.wa2.poll.frontend.exception.ClientException;
import org.apache.log4j.Logger;

import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


public class VoterClient {

    WebTarget target;

    final static Logger logger = Logger.getLogger(VoterClient.class);

    public VoterClient(String address) {
        target = ClientStore.getClient().target(address).path("/rest/voter");
    }


    // @GET
    // /voter/login
    public VoterDTO authorizeVoter(String email, String password) {
        VoterDTO voter = null;
        WebTarget resourceTarget = target.path("/login").queryParam("email", email).queryParam("password", password);
        Invocation.Builder invocationBuilder = resourceTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("authorizeVoter.status = " + status);
        }

        if (status == 200) {
            voter = response.readEntity(VoterDTO.class);
        }
        response.close();
        return voter;
    }

    // @GET
    public List<VoterDTO> getVoters() {
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("getVoters.status = " + status);
        }

        if (status == 200) {
            List<VoterDTO> voters = response.readEntity(new GenericType<List<VoterDTO>>() {
            });
            response.close();
            return voters;
        } else {
            return new ArrayList<VoterDTO>();
        }
    }

    // @GET
    // /voter/{id}
    public VoterDTO getVoter(Long id) {
        WebTarget resourceTarget = target.path("/" + id);
        Invocation.Builder invocationBuilder = resourceTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("getVoter.status = " + status);
        }

        if (status == 200) {
            VoterDTO voters = response.readEntity(VoterDTO.class);
            response.close();
            return voters;
        } else {
            return null;
        }
    }

    // @GET
    public List<VoterGroupDTO> getSupervisedGroups(Long id) {
        return getGroups(id, 2);
    }

    // @GET
    public List<VoterGroupDTO> getRegistredGroups(Long id) {
        return getGroups(id, 1);
    }

    // @GET
    public List<VoterGroupDTO> getNotregistredGroups(Long id) {
            return getGroups(id, 0);
    }

    public List<VoterGroupDTO> getGroups(Long id, Integer findWho) {
        WebTarget resourceTarget = target.path("/" + id).path("/group").queryParam("findWho",findWho);
        Invocation.Builder invocationBuilder = resourceTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("geGroups.status = " + status);
        }

        if (status == 200) {
            List<VoterGroupDTO> voters = response.readEntity(new GenericType<List<VoterGroupDTO>>() {
            });
            response.close();
            return voters;
        } else {
            return new ArrayList<VoterGroupDTO>();
        }
    }

    // @POST
    // /voter
    public void saveVoterGroup(Long voterId, VoterGroupDTO voter) throws ClientException {
        WebTarget resourceTarget = target.path("/" + voterId).path("/group");
        Invocation.Builder invocationBuilder = resourceTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.entity(voter, MediaType.APPLICATION_JSON));
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("saveVoterGroup.status = " + status);
        }

        if (status == 200) {
            response.close();
        }else{
            String error = response.readEntity(String.class);
            response.close();
            throw new ClientException(error);
        }

    }

    // @POST
    // /voter
    public void saveVoter(VoterDTO voterDTO) throws ClientException {
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.entity(voterDTO, MediaType.APPLICATION_JSON));
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("saveVoter.status = " + status);
        }

        if (status == 200) {
            List<VoterGroupDTO> voters = response.readEntity(new GenericType<List<VoterGroupDTO>>() {
            });
            response.close();
        }else{
            throw new ClientException((String)response.readEntity(String.class));
        }
    }

    public void updateVoter(VoterDTO voterDTO) throws ClientException {
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.put(Entity.entity(voterDTO, MediaType.APPLICATION_JSON));
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("updateVoter.status = " + status);
        }

        if (status == 200) {
            List<VoterGroupDTO> voters = response.readEntity(new GenericType<List<VoterGroupDTO>>() {
            });
            response.close();
        }else{
            throw new ClientException((String)response.readEntity(String.class));
        }
    }

    // @GET
    // @Path(value = "/{id}/nonvoted_polls")
    public List<PollDTO> getNonvotedPolls(Long id) {
        return getPolls(id,0);
    }

    // @GET
    // @Path(value = "/{id}/voted_polls")
    public List<PollDTO> getVotedPolls(Long id) {
        return getPolls(id,1);
    }

    // @GET
    // @Path(value = "/{id}/voted_polls")
    public List<PollDTO> getSupervisedPolls(Long id) {
        return getPolls(id,2);
    }

    public List<PollDTO> getPolls(Long id, Integer voted) {
        WebTarget resourceTarget = target.path("/" + id).path("/poll").queryParam("voted",voted);
        Invocation.Builder invocationBuilder = resourceTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("getPolls.status = " + status);
        }

        if (status == 200) {
            List<PollDTO> voters = response.readEntity(new GenericType<List<PollDTO>>() {
            });
            response.close();
            return voters;
        } else {
            return new ArrayList<PollDTO>();
        }
    }

    // @GET
    // @Path(value = "/{voter}/poll/{poll}/ballot")
    public BallotDTO getBallot(Long voterId, Long pollId) {
        WebTarget resourceTarget = target.path("/" + voterId).path("/poll").path("/" + pollId).path("/ballot");
        Invocation.Builder invocationBuilder = resourceTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("getNonvotedPolls.status = " + status);
        }

        if (status == 200) {
            BallotDTO ballotDTO = response.readEntity(BallotDTO.class);
            response.close();
            return ballotDTO;
        } else {
            return null;
        }
    }

}
