package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.VoterDTO;
import cz.wa2.poll.frontend.rest.VoterClient;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean(name = "login")
@RequestScoped
public class LoginController implements Serializable {

    @ManagedProperty(value = "#{voter}")
    LoggedVoter loggedVoter;

    private String email;
    private String password;

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
