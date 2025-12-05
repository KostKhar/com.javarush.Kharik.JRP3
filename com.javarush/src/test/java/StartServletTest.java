import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import quest.Answer;
import quest.Quest;
import quest.Question;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StartServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    private StartServlet servlet;
    private Quest quest;
    private Question question1, question2;

    @BeforeEach
    void setUp() {
        servlet = new StartServlet();

        question1 = Mockito.mock(Question.class);
        question2 = Mockito.mock(Question.class);

        List<Question> questions = Arrays.asList(question1, question2);
        quest = new Quest();
        quest.setName("Test Quest");
        quest.setDescription("Test Description");
        quest.setQuestions(questions);
        quest.setCurrentQuestion(question1);
    }


    @Test
    void testDoPost_InvalidAnswer_ShouldShowError() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("currentQuest")).thenReturn(quest);
        when(session.getAttribute("currentQuestion")).thenReturn(question1);
        when(request.getParameter("answer")).thenReturn("invalid");
        when(request.getRequestDispatcher("/main.jsp")).thenReturn(requestDispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Неверный ответ");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPost_NoSession_ShouldRedirect() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("currentQuest")).thenReturn(null);
        when(request.getContextPath()).thenReturn("/context");

        servlet.doPost(request, response);

        verify(response).sendRedirect("/context/start");
    }


    @Test
    void testFindQuestionById_NonExistingId_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            servlet.findQuestionById(quest, 999);
        });
    }
}