package cz.wa2.poll.frontend.websocket;

import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;

@PushEndpoint("/hlasovani")
public class QueueResource {

    @OnMessage(encoders = {JSONEncoder.class})
    public String onMessage(String message) {
        System.out.println("uprava");
        return message;
    }
}
