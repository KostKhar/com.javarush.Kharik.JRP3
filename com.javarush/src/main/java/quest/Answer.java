package quest;

import lombok.Data;

@Data
public class Answer {
    private Integer id;
    private String answerText;
    private Integer nextQuestionId;
    private Question nextQuestion;

    public Answer(String answerText, Integer nextQuestionId) {
        this.answerText = answerText;
        this.nextQuestionId = nextQuestionId;
    }
}
