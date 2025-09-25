import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;


@Data
@Getter
public class Quest {
    private Long id;
    private String name;
    private String description;
    private Long startQuestionId;
    private Question startQuestion;
    private Map<Long, Question> questions = new HashMap<>();



    public void setQuest(Path path){
        ObjectMapper objectMapper = new ObjectMapper();
        Quest quest = null;
            try {
                 quest = objectMapper.readValue(path.toFile(), Quest.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(quest == null) throw new NullPointerException("Пожалуйста, проверьте правильный ли файл вы указали");
    }
}
