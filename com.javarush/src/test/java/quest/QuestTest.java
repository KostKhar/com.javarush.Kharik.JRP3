package quest;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestTest {
    @Mock
    Quest questMock;

    @Test
    void testSetQuest_Success() {
        Quest quest =  new Quest();

        // Файл лежит в test/resources
        Path path = Path.of("src/main/resources/quest.json");

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

         assertThrows(NullPointerException.class, () -> {questMock.setQuest(path);});

    }

    @Test
    void testSetQuest_InvalidJson(@TempDir Path tempDir) throws Exception {
        Path invalidJson = tempDir.resolve("bad.json");
        java.nio.file.Files.writeString(invalidJson, "{ invalid json }");

        assertThrows(RuntimeException.class, () -> {questMock.setQuest(invalidJson);});

    }

}