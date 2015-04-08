package cz.wa2.poll.frontend.rest;

import cz.wa2.poll.frontend.dto.BallotDTO;
import cz.wa2.poll.frontend.dto.PollDTO;
import cz.wa2.poll.frontend.dto.VoterDTO;
import cz.wa2.poll.frontend.dto.VoterGroupDTO;
import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


public class VoterClient {

    Client restClient = ClientBuilder.newClient();
    WebTarget target = restClient.target("http://localhost:8080/rest/voter");

    final static Logger logger = Logger.getLogger(VoterClient.class);

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
    // /voter
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
    // /voter/{id}/supervised_groups
    public List<VoterGroupDTO> getSupervisedGroups(Long id) {
        WebTarget resourceTarget = target.path("/" + id).path("/supervised_groups");
        Invocation.Builder invocationBuilder = resourceTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("getSupervisedGroups.status = " + status);
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

    // @GET
    // /voter/{id}/registred_groups
    public List<VoterGroupDTO> getRegistredGroups(Long id) {
        WebTarget resourceTarget = target.path("/" + id).path("/registred_groups");
        Invocation.Builder invocationBuilder = resourceTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("getRegistredGroups.status = " + status);
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

    // @GET
    // /voter/{id}/notregistred_groups
    public List<VoterGroupDTO> getNotregistredGroups(Long id) {
        WebTarget resourceTarget = target.path("/" + id).path("/notregistred_groups");
        Invocation.Builder invocationBuilder = resourceTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("getNotregistredGroups.status = " + status);
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
    public void saveVoter(VoterDTO voter) {
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.entity(voter, MediaType.APPLICATION_JSON));
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("saveVoter.status = " + status);
        }

        if (status == 200) {
            List<VoterGroupDTO> voters = response.readEntity(new GenericType<List<VoterGroupDTO>>() {
            });
            response.close();
        }
    }

    // @GET
    // @Path(value = "/{id}/nonvoted_polls")
    public List<PollDTO> getNonvotedPolls(Long id) {
        WebTarget resourceTarget = target.path("/" + id).path("/nonvoted_polls");
        Invocation.Builder invocationBuilder = resourceTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("getNonvotedPolls.status = " + status);
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
    // @Path(value = "/{id}/voted_polls")
    public List<PollDTO> getVotedPolls(Long id) {
        WebTarget resourceTarget = target.path("/" + id).path("/voted_polls");
        Invocation.Builder invocationBuilder = resourceTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("getVotedPolls.status = " + status);
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
