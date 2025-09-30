import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import quest.Answer;
import quest.Quest;
import quest.Question;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StartServletTest extends HttpServlet {

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
    private Question question1, question2, finalQuestion;

    @BeforeEach
    void setUp() {
        servlet = new StartServlet();

        // Создаем тестовые данные
        Answer answer1 = new Answer("Yes", 2);
        Answer answer2 = new Answer("Нет", 1);
        Answer finalAnswer = new Answer("Завершить", 0);

        question1 = new Question(1, "Первый вопрос", Arrays.asList(answer1, answer2));
        question2 = new Question(2, "Второй вопрос", Arrays.asList(answer1, answer2));
        finalQuestion = new Question(3, "Финальный вопрос", Arrays.asList(finalAnswer));

        List<Question> questions = Arrays.asList(question1, question2, finalQuestion);
        quest = new Quest();
        quest.setName("Test Quest");
        quest.setDescription("Test Description");
        quest.setQuestions(questions);
        quest.setCurrentQuestion(question1);
    }

    @Test
    void testDoGet_NewSession_ShouldCreateNewQuest() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("currentQuest")).thenReturn(null);
        when(request.getRequestDispatcher("/main.jsp")).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(session).setAttribute(eq("currentQuest"), any(Quest.class));
        verify(request).setAttribute("name", "Test Quest");
        verify(request).setAttribute("description", "Test Description");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoGet_ExistingSession_ShouldUseExistingQuest() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("currentQuest")).thenReturn(quest);
        when(session.getAttribute("currentQuestion")).thenReturn(question1);
        when(request.getRequestDispatcher("/main.jsp")).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(session, never()).setAttribute(eq("currentQuest"), any(Quest.class));
        verify(request).setAttribute("name", "Test Quest");
        verify(request).setAttribute("description", "Test Description");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPost_YesAnswer_ShouldGoToNextQuestion() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("currentQuest")).thenReturn(quest);
        when(session.getAttribute("currentQuestion")).thenReturn(question1);
        when(request.getParameter("answer")).thenReturn("yes");
        when(request.getRequestDispatcher("/main.jsp")).thenReturn(requestDispatcher);

        servlet.doPost(request, response);

        verify(session).setAttribute("currentQuestion", question2);
        verify(request).setAttribute("question", "Второй вопрос");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPost_NoAnswer_ShouldFinishGame() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("currentQuest")).thenReturn(quest);
        when(session.getAttribute("currentQuestion")).thenReturn(question1);
        when(request.getParameter("answer")).thenReturn("no");
        when(request.getRequestDispatcher("/finish.jsp")).thenReturn(requestDispatcher);

        servlet.doPost(request, response);

        verify(session).removeAttribute("currentQuest");
        verify(session).removeAttribute("currentQuestion");
        verify(requestDispatcher).forward(request, response);
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
    void testDoPost_FinalQuestion_ShouldShowFinishPage() throws ServletException, IOException {
        quest.setCurrentQuestion(finalQuestion);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("currentQuest")).thenReturn(quest);
        when(session.getAttribute("currentQuestion")).thenReturn(finalQuestion);
        when(request.getParameter("answer")).thenReturn("yes");
        when(request.getRequestDispatcher("/finish.jsp")).thenReturn(requestDispatcher);

        servlet.doPost(request, response);

        verify(session).removeAttribute("currentQuest");
        verify(session).removeAttribute("currentQuestion");
        verify(request).setAttribute("name", "Test Quest");
        verify(request).setAttribute("question", "Финальный вопрос");
        verify(request).setAttribute("yes", "Завершить");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPost_AnswerIndexOutOfBounds_ShouldShowError() throws ServletException, IOException {
        Question questionWithOneAnswer = new Question(4, "Один ответ",
                Arrays.asList(new Answer("Единственный ответ", 0)));
        quest.setCurrentQuestion(questionWithOneAnswer);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("currentQuest")).thenReturn(quest);
        when(session.getAttribute("currentQuestion")).thenReturn(questionWithOneAnswer);
        when(request.getParameter("answer")).thenReturn("no"); // Пытаемся получить второй ответ
        when(request.getRequestDispatcher("/main.jsp")).thenReturn(requestDispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Ошибка: ответ не найден");
        verify(requestDispatcher).forward(request, response);
    }

}