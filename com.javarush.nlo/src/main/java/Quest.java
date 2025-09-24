import lombok.Data;

import java.util.List;


@Data
public class Quest {
    private int id;
    private List<Question> questions;
}
