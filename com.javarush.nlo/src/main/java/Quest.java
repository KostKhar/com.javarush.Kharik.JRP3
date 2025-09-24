import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


@Data
@Getter
public class Quest {
    private Long id;
    private String name;
    private String description;
    private Question startQuestion;
    private Map<Long, Question> questions = new HashMap<>();
}
