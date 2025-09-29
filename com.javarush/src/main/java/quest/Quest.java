package quest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;


@Data
@Getter
public class Quest {
    private Integer id;
    private String name;
    private String description;
    private Long currentQuestionId;
    private Question currentQuestion;
    private List<Question> questions = new LinkedList<>();


    public void setQuest(Path path) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path.toString())) {
            if (inputStream == null) {
                throw new RuntimeException("Файл не найден.");
            }
            Quest loadedQuest = objectMapper.readValue(inputStream, Quest.class);
            this.id = loadedQuest.getId();
            this.name = loadedQuest.getName();
            this.description = loadedQuest.getDescription();
            this.currentQuestionId = loadedQuest.getCurrentQuestionId();
            this.currentQuestion = loadedQuest.getCurrentQuestion();
            this.questions = loadedQuest.getQuestions();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки квеста: " + e.getMessage(), e);
        }
    }

}