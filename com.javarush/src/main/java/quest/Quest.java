package quest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Data
public class Quest {
    static final Logger logger = LoggerFactory.getLogger(Quest.class);
    private Integer id;
    private String name;
    private String description;
    private Integer currentQuestionId;
    private Question currentQuestion;
    private List<Question> questions = new LinkedList<>();


    public void setQuest(Path path) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path.toString())) {
            Quest loadedQuest = objectMapper.readValue(inputStream, Quest.class);
            this.id = loadedQuest.getId();
            this.name = loadedQuest.getName();
            this.description = loadedQuest.getDescription();
            this.currentQuestionId = loadedQuest.getCurrentQuestionId();
            this.currentQuestion = loadedQuest.getCurrentQuestion();
            this.questions = loadedQuest.getQuestions();
        } catch (Exception e) {
            logger.error("File not found or not supported");
            throw new RuntimeException("Exception: " + e.getMessage(), e);
        }
    }

}