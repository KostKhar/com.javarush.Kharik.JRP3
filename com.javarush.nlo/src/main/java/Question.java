import lombok.Data;

import java.util.List;

@Data
public class Question {
    private int id;
    private String question;
    private List<Answer>  answers;

    public Question(String question, List<Answer> answers) {
        this.question = question;
        this.answers = answers;
    }
}
