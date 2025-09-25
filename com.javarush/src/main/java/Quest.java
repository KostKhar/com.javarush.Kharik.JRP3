import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Data
@Getter
public class Quest {
    private Long id;
    private String name;
    private String description;
    private Long startQuestionId;
    private Question startQuestion;
    private List<Question> questions = new LinkedList<>();


    public void setQuest(Path path) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path.toString())) {
            if (inputStream == null) {
                throw new RuntimeException("Файл не найден.");
            }
            Quest loadedQuest = objectMapper.readValue(inputStream, Quest.class);
            this.description = loadedQuest.getDescription();
            this.startQuestion = loadedQuest.getStartQuestion();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки квеста: " + e.getMessage(), e);
        }
    }
}