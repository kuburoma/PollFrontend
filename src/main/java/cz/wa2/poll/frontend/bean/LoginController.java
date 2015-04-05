package cz.wa2.poll.frontend.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.client.*;
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.GenericType;
//import com.sun.jersey.api.client.WebResource;
//import com.sun.jersey.api.client.ClientResponse;
import cz.wa2.poll.frontend.dto.VoterDTO;
import cz.wa2.poll.frontend.exception.ClientException;
import cz.wa2.poll.frontend.rest.VoterClient;
import cz.wa2.poll.frontend.rest.VoterGroupClient;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "login")
@RequestScoped
public class LoginController implements Serializable {

    @ManagedProperty(value = "#{voter}")
    LoggedVoter loggedVoter;

    private String email;
    private String password;

   /* @PostConstruct
    public void postConstruct() {
        if (loggedVoter.getVoter() != null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().dispatch("/create-group.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    public String login() {
        VoterClient vc = new VoterClient();
        VoterDTO vd = vc.authorizeVoter(email, password);
        if (vd != null) {
            loggedVoter.setVoter(vd);
            return "success";
        } else {
            RequestContext.getCurrentInstance().update("growl");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Email enbo heslo je špatně"));
            return "";
        }
    }

    public String logout() {
        loggedVoter.setVoter(null);
        return "login.xhtml";
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

    public LoggedVoter getLoggedVoter() {
        return loggedVoter;
    }

    public void setLoggedVoter(LoggedVoter loggedVoter) {
        this.loggedVoter = loggedVoter;
    }
}
