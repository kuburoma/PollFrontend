package cz.wa2.poll.frontend.rest;

import cz.wa2.poll.frontend.dto.BallotDTO;
import cz.wa2.poll.frontend.dto.PollDTO;
import cz.wa2.poll.frontend.dto.VoterDTO;
import cz.wa2.poll.frontend.dto.VoterGroupDTO;
import org.apache.log4j.Logger;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class BallotClient {

    Client restClient = ClientBuilder.newClient();
    WebTarget target = restClient.target("http://localhost:8080/rest/ballot");

    final static Logger logger = Logger.getLogger(BallotClient.class);

    // @PUT
    public void updateBallot(BallotDTO ballotDTO) {
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.put(Entity.entity(ballotDTO, MediaType.APPLICATION_JSON));
        int status = response.getStatus();

        if (logger.isDebugEnabled()) {
            logger.debug("updateBallot.status = " + status);
        }

        if (status == 200) {

        }
        response.close();
    }



}
