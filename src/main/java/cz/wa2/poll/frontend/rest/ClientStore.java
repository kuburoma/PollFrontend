package cz.wa2.poll.frontend.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by Nell on 13.4.2015.
 */
public class ClientStore {

    private static Client restClient;

    protected ClientStore() {
    }

    public static Client getClient(){
        if(restClient == null){
            restClient = ClientBuilder.newClient();
        }
        return restClient;
    }


}
