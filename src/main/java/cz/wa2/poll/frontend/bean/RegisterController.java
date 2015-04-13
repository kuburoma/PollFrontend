package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.dto.VoterDTO;
import cz.wa2.poll.frontend.exception.ClientException;
import cz.wa2.poll.frontend.rest.VoterClient;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean(name = "register")
@RequestScoped
public class RegisterController extends UniversalController implements Serializable {

    final static Logger logger = Logger.getLogger(RegisterController.class);

    VoterClient voterClient;

    @ManagedProperty(value = "#{voter}")
    LoggedVoter loggedVoter;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public String register(){

        if(logger.isDebugEnabled()){
            logger.debug("This is debug");
        }

        VoterDTO voter = new VoterDTO();
        voter.setFirstName(firstName);
        voter.setLastName(lastName);
        voter.setEmail(email);
        voter.setPassword(password);

        voterClient = new VoterClient(loggedVoter.getRestServerAddress());

        try {
            voterClient.saveVoter(voter);
            addMessage(FacesMessage.SEVERITY_INFO, "Info", "Úspěšně jste se zaregistrovali.");
            return "/login.xhtml";
        } catch (ClientException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            return "";
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
