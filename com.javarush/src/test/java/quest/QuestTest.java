package quest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        assertEquals("1", quest.getId());
        assertEquals("IT Career Quest", quest.getName());
        assertEquals("История начинается с того, что ты просыпаешься и понимаешь, что тебя съедает рутина и хочется чего то нового", quest.getDescription());
        assertEquals("1", quest.getCurrentQuestionId());
        assertEquals("Хочешь начать карьеру в IT?", quest.getCurrentQuestion().getQuestionText());
        assertEquals(2, quest.getCurrentQuestion().getAnswers().size());
    }

    @Test
    void testSetQuest_FileNotFound() {

        Path path = Path.of("nonexistent.json");

        assertThrows(NullPointerException.class, () -> {
            questMock.setQuest(path);
        });

    }

    @Test
    void testSetQuest_InvalidJson(@TempDir Path tempDir) throws Exception {
        Path invalidJson = tempDir.resolve("bad.json");
        java.nio.file.Files.writeString(invalidJson, "{ invalid json }");

        assertThrows(RuntimeException.class, () -> {
            questMock.setQuest(invalidJson);
        });

    }

}