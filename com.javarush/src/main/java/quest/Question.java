package quest;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Question {
    private Integer id;
    private String questionText;
    private List<Answer> answers = new ArrayList<>();

}
