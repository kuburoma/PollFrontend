package cz.wa2.poll.frontend.bean;

import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Created by Nell on 11.4.2015.
 */
public class UniversalController {

    protected void addMessage(FacesMessage.Severity severity, String header, String message){
        RequestContext.getCurrentInstance().update("growl");
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severity, header, message));
    }
}
