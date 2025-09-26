package quest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Question {
    private Long id;
    private String questionText;
    private List<Answer> answers = new ArrayList<>();

    public Question getQuestionFromRequestParam(String req){
        ObjectMapper mapper = new ObjectMapper();
        Question question = null;
        try{
            question = mapper.readValue(req, Question.class);
        } catch (JsonProcessingException e) {
            System.err.println("Sorry, couldn't parse the request");
        }
        return question;
    }
}
