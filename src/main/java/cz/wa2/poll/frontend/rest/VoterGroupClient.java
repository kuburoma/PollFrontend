package cz.wa2.poll.frontend.rest;

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

public class VoterGroupClient {

    Client restClient = ClientBuilder.newClient();
    WebTarget target = restClient.target("http://localhost:8080/rest/votergroup");

    final static Logger logger = Logger.getLogger(VoterClient.class);

    // @POST
    public void create(VoterGroupDTO voterGroupDTO) {
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.entity(voterGroupDTO, MediaType.APPLICATION_JSON));
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("create.status = " + status);
        }
        response.close();
    }

    public void update(VoterGroupDTO voterGroupDTO) {
        WebTarget resourceTarget = target.path("/" + voterGroupDTO.getId());
        Invocation.Builder invocationBuilder = resourceTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.put(Entity.entity(voterGroupDTO, MediaType.APPLICATION_JSON));
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("authorizeVoter.status = " + status);
        }
        response.close();
    }

    // @GET
    // @Path("/{id}/voters")
    public List<VoterDTO> getVoters(Long id) throws ClientException {
        WebTarget resourceTarget = target.path("/" + id).path("/voters");
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
            throw new ClientException("REST "+resourceTarget.getUri().toString()+" response: " + status);
        }
    }

}
