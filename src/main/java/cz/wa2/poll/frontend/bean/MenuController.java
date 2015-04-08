package cz.wa2.poll.frontend.bean;

import cz.wa2.poll.frontend.rest.VoterClient;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "menu")
@SessionScoped
public class MenuController {

    VoterClient voterClient = new VoterClient();

    @ManagedProperty(value="#{voter}")
    LoggedVoter loggedVoter;


    public LoggedVoter getLoggedVoter() {
        return loggedVoter;
    }

    public void setLoggedVoter(LoggedVoter loggedVoter) {
        this.loggedVoter = loggedVoter;
    }

    public String supervisedGroups(){
        loggedVoter.setVoterGroupDTOList(voterClient.getSupervisedGroups(loggedVoter.getVoter().getId()));
        return "/supervised-groups.xhtml";
    }

    public String registerGroups(){
        loggedVoter.setVoterGroupDTOList(voterClient.getNotregistredGroups(loggedVoter.getVoter().getId()));
        return "/register-group.xhtml";
    }

    public String unregisterGroups(){
        loggedVoter.setVoterGroupDTOList(voterClient.getRegistredGroups(loggedVoter.getVoter().getId()));
        return "/unregister-group.xhtml";
    }

    public String vote(){
        loggedVoter.setPollDTOList(voterClient.getNonvotedPolls(loggedVoter.getVoter().getId()));
        return "/unvoted-poll.xhtml";
    }

    public String voted(){
        loggedVoter.setPollDTOList(voterClient.getVotedPolls(loggedVoter.getVoter().getId()));
        return "/voted-poll.xhtml";
    }


}
