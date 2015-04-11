package cz.wa2.poll.frontend.dto;

import java.io.Serializable;

/**
 * Created by Nell on 12.4.2015.
 */
public class AnswerHelper implements Serializable {

    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
