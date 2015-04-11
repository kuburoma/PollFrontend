package cz.wa2.poll.frontend.rest;

import cz.wa2.poll.frontend.dto.BallotDTO;
import cz.wa2.poll.frontend.dto.PollDTO;
import org.apache.log4j.Logger;

import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


public class PollClient {

    Client restClient = ClientBuilder.newClient();
    WebTarget target = restClient.target("http://localhost:8080/rest/poll");

    final static Logger logger = Logger.getLogger(PollClient.class);

    // @GET
    // @Path(value = "/{id}/ballot")
    public List<BallotDTO> getPollBallots(Long id) {
        WebTarget resourceTarget = target.path("/" + id).path("/ballot");
        Invocation.Builder invocationBuilder = resourceTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("getPollBallots.status = " + status);
        }

        if (status == 200) {
            List<BallotDTO> voters = response.readEntity(new GenericType<List<BallotDTO>>() {
            });
            response.close();
            return voters;
        } else {
            return new ArrayList<BallotDTO>();
        }
    }

}
