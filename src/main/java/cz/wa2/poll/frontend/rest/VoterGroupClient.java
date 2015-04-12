package cz.wa2.poll.frontend.rest;

import cz.wa2.poll.frontend.dto.PollDTO;
import cz.wa2.poll.frontend.dto.VoterDTO;
import cz.wa2.poll.frontend.dto.VoterGroupDTO;
import cz.wa2.poll.frontend.exception.ClientException;
import org.apache.log4j.Logger;

import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class VoterGroupClient {

    Client restClient = ClientBuilder.newClient();
    WebTarget target = restClient.target("http://localhost:8080/rest/votergroup");

    final static Logger logger = Logger.getLogger(VoterClient.class);

    // @GET
    // @Path("/{id}/voters")
    public List<VoterDTO> getVoters(Long id) throws ClientException {
        WebTarget resourceTarget = target.path("/" + id).path("/voter");
        Invocation.Builder invocationBuilder = resourceTarget.request(MediaType.APPLICATION_JSON_TYPE);
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
            response.close();
            throw new ClientException("REST response: " + status);
        }
    }

    // @PUT
    // @Path("/{votergroup}/voter/{voter}")
    public void putVoterToVotergroup(Long votergroup, Long voter) throws ClientException {
        WebTarget resourceTarget = target.path("/" + votergroup).path("/voter").path("/" + voter);
        Invocation.Builder invocationBuilder = resourceTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.put(Entity.entity(new VoterDTO(), MediaType.APPLICATION_JSON));
        int status = response.getStatus();
        response.close();

        if (logger.isDebugEnabled()) {
            logger.debug("putVoterToVotergroup.status = " + status);
        }

        if (status != 200) {
            throw new ClientException("REST response: " + status);
        }
    }

    // @DELETE
    // @Path("/{votergroup}/voter/{voter}")
    public void deleteVoterFromVotergroup(Long votergroup, Long voter) throws ClientException {
        WebTarget resourceTarget = target.path("/" + votergroup).path("/voter").path("/" + voter);
        Invocation.Builder invocationBuilder = resourceTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.delete();
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("deleteVoterFromVotergroup.status = " + status);
        }
        response.close();
        if (status != 200) {
            throw new ClientException("");
        }
    }

    // @POST
    // @Path("/{id}/poll")
    public void createPoll(PollDTO pollDTO, Long votergroup) throws ClientException {
        WebTarget resourceTarget = target.path("/" + votergroup).path("/poll");
        Invocation.Builder invocationBuilder = resourceTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.entity(pollDTO, MediaType.APPLICATION_JSON));
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("putVoterToVotergroup.status = " + status);
        }

        if (status != 200) {
            String error = response.readEntity(String.class);
            response.close();
            throw new ClientException(error);
        }
        response.close();
    }

    // @POST
    // @Path("/{id}")
    public void deleteVoterGroup(Long votergroup) throws ClientException {
        WebTarget resourceTarget = target.path("/" + votergroup);
        Invocation.Builder invocationBuilder = resourceTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.delete();
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("deleteVoterGroup.status = " + status);
        }

        if (status != 200) {
            String error = response.readEntity(String.class);
            response.close();
            throw new ClientException(error);
        }
        response.close();
    }
}
