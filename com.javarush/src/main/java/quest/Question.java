package quest;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Question {
    private Long id;
    private String questionText;
    private List<Answer> answers = new ArrayList<>();
}
