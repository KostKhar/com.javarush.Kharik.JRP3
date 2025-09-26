package quest;

import lombok.Data;

@Data
public class Answer {
    private Long id;
    private String answerText;
    private Long nextQuestionId;
    private Question nextQuestion;
    private Type type;
}
