package cz.wa2.poll.frontend.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PollDTO {

    private Long id;
    private String name;
    private String question;
    private List<AnswerHelper> answers = new ArrayList<AnswerHelper>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<AnswerHelper> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerHelper> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Name: "+name+"\n");
        sb.append("Question: "+question+"\n");
        return sb.toString();
    }
}
