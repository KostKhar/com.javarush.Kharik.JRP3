import lombok.Data;

@Data
public class Answer {
    private String answer;
    private Question question;

    public Answer(String answer, Question question) {
        this.answer = answer;
        this.question = question;
    }
}
