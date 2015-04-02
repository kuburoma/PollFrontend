package cz.wa2.poll.frontend.bean;

import javax.ws.rs.client.*;
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.GenericType;
//import com.sun.jersey.api.client.WebResource;
//import com.sun.jersey.api.client.ClientResponse;
import cz.wa2.poll.frontend.dto.VoterDTO;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "login")
@SessionScoped
public class LoginController implements Serializable {

    private String username;
    private String password;


    public String loginControl(){
        testRest();
        if(username.equals("ahoj") && password.equals("ahoj")){
            return "success";
        }else{
            RequestContext.getCurrentInstance().update("growl");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Email enbo heslo je špatně"));
            return "";
        }
    }

    public void testRest(){

        Client restClient = ClientBuilder.newClient();

        WebTarget target = restClient.target("http://localhost:8080/rest");

        WebTarget resourceTarget = target.path("/voter"); //change the URI without affecting a root URI
        List<VoterDTO> voters = resourceTarget.request(MediaType.APPLICATION_JSON_TYPE).get(new GenericType<List<VoterDTO>>(){});
        for (VoterDTO v : voters){
            System.out.println(v.toString());
        }

/*        try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource("http://localhost:8080/rest/voter");

            ClientResponse response = webResource.accept("application/json")
                    .get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
            GenericType<List<VoterDTO>> gType = new GenericType<List<VoterDTO>>(){};

            List<VoterDTO> voters = response.getEntity(gType);
            for (VoterDTO v : voters){
                System.out.println(v.toString());
            }



        } catch (Exception e) {

            e.printStackTrace();

        }*/
    }

    public String pozdrav(){
        return "AHOJ";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
