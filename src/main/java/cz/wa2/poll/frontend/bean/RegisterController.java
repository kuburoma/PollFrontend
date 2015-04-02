package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.VoterDTO;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;

//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.GenericType;
//import com.sun.jersey.api.client.WebResource;
//import com.sun.jersey.api.client.ClientResponse;

@ManagedBean(name = "register")
@RequestScoped
public class RegisterController implements Serializable {

    final static Logger logger = Logger.getLogger(RegisterController.class);

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public void register(){

        if(logger.isDebugEnabled()){
            logger.debug("This is debug");
        }

        VoterDTO voter = new VoterDTO();
        voter.setFirstName(firstName);
        voter.setLastName(lastName);
        voter.setEmail(email);
        voter.setPassword(password);

        Client restClient = ClientBuilder.newClient();

        WebTarget target = restClient.target("http://localhost:8080/rest");

        WebTarget resourceTarget = target.path("/voter"); //change the URI without affecting a root URI
        Response response = resourceTarget.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(voter, MediaType.APPLICATION_JSON));
        if (response.getStatus() != 200) {
            throw new RuntimeException("Error occurred on server "
                    + response.getStatus());
        }

    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
