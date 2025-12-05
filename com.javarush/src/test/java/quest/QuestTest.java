package quest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuestTest {
    @Mock
    Quest questMock;

    @Test
    void testSetQuest_Success() {
        Quest quest = new Quest();
        // Файл лежит в test/resources
        Path path = Path.of("quest.json");

        quest.setQuest(path);

        assertTrue(quest.getId() > 0);
        assertEquals("IT Career Quest", quest.getName());
        assertEquals("История начинается с того, что ты просыпаешься и понимаешь, что тебя съедает рутина и хочется чего то нового", quest.getDescription());
        assertTrue(quest.getCurrentQuestionId() > 0);
        assertEquals("Хочешь начать карьеру в IT?", quest.getCurrentQuestion().getQuestionText());
        assertEquals(2, quest.getCurrentQuestion().getAnswers().size());
    }

    @Test
    void testSetQuest_FileNotFound() {
        Path path = Path.of("quest.json");
        Mockito.doThrow(RuntimeException.class).when(questMock).setQuest(path);

        assertThrows(RuntimeException.class, () -> {
            questMock.setQuest(path);
        });

    }

}