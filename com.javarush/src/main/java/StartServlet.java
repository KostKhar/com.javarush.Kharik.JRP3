import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quest.Answer;
import quest.Quest;
import quest.Question;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


@Slf4j
@WebServlet(name = "StartServlet", value = "/start")
public class StartServlet extends HttpServlet {
    static final Logger logger = LoggerFactory.getLogger(Quest.class);


    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        HttpSession session = req.getSession();

        // Создаем новый квест или получаем существующий из сессии
        Quest quest = (Quest) session.getAttribute("currentQuest");
        if (quest == null) {
            quest = new Quest();
            quest.setQuest(Path.of("quest.json"));
            session.setAttribute("currentQuest", quest);
            logger.info("Game started");
        }

        Question currentQuestion = quest.getCurrentQuestion();
        session.setAttribute("currentQuestion", currentQuestion);

        showQuestion(req, resp, currentQuestion, quest);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        String answer = req.getParameter("answer");

        Quest quest = (Quest) session.getAttribute("currentQuest");
        Question currentQuestion = (Question) session.getAttribute("currentQuestion");

        if (quest == null || currentQuestion == null) {
            resp.sendRedirect(req.getContextPath() + "/start");
            logger.error("session was blocked");
            return;
        }


        int answerIndex;
        if (answer.equals("yes")) {
            answerIndex = 0;
        } else if (answer.equals("no")) {
            answerIndex = 1;
        } else {
            req.setAttribute("error", "Неверный ответ");
            showQuestion(req, resp, currentQuestion, quest);
            logger.error("Answer is not supported");
            return;
        }

        // Проверяем, что индекс ответа существует
        List<Answer> answers = currentQuestion.getAnswers();
        if (answerIndex >= answers.size()) {
            req.setAttribute("error", "Ошибка: ответ не найден");
            showQuestion(req, resp, currentQuestion, quest);
            logger.error("answer is incorrect");
            return;
        }

        int nextQuestionId = answers.get(answerIndex).getNextQuestionId();

        if (nextQuestionId == 0) {
            session.removeAttribute("currentQuest");
            session.removeAttribute("currentQuestion");
            req.getRequestDispatcher("/finish.jsp").forward(req, resp);
            logger.info("User was win " + session.getId());
            return;
        }

        Question nextQuestion = findQuestionById(quest, nextQuestionId);

        if (nextQuestion == null) {
            req.setAttribute("error", "Ошибка: следующий вопрос не найден");
            showQuestion(req, resp, currentQuestion, quest);
            logger.error("answer is incorrect");
            return;
        }

        quest.setCurrentQuestion(nextQuestion);
        session.setAttribute("currentQuestion", nextQuestion);

        // Проверяем, является ли следующий вопрос победным (имеет только один ответ)
        if (nextQuestion.getAnswers().size() == 1) {
            // Победа!
            session.removeAttribute("currentQuest");
            session.removeAttribute("currentQuestion");
            showFinishPage(req, resp, quest);
        } else {
            // Показываем следующий вопрос
            showQuestion(req, resp, nextQuestion, quest);
        }
    }

    private void showQuestion(HttpServletRequest req, HttpServletResponse resp,
                              Question question, Quest quest)
            throws ServletException, IOException {

        req.setAttribute("name", quest.getName());
        req.setAttribute("description", quest.getDescription());
        req.setAttribute("question", question.getQuestionText());

        List<Answer> answers = question.getAnswers();

        if (answers.size() == 1) {
            req.setAttribute("yes", answers.get(0).getAnswerText());
            req.getRequestDispatcher("/finish.jsp").forward(req, resp);
        } else if (answers.size() >= 2) {
            // Обычный вопрос с двумя ответами
            req.setAttribute("yes", answers.get(0).getAnswerText());
            req.setAttribute("no", answers.get(1).getAnswerText());
            req.getRequestDispatcher("/main.jsp").forward(req, resp);
        } else {
            logger.error("Answers was not found");
            req.getRequestDispatcher("/main.jsp").forward(req, resp);
        }
    }

    private void showFinishPage(HttpServletRequest req, HttpServletResponse resp, Quest quest)
            throws ServletException, IOException {
        req.setAttribute("name", quest.getName());
        req.setAttribute("question", quest.getCurrentQuestion().getQuestionText());
        req.setAttribute("yes", quest.getCurrentQuestion().getAnswers().get(0).getAnswerText());
        req.getRequestDispatcher("/finish.jsp").forward(req, resp);
    }

    public Question findQuestionById(Quest quest, int id) {
        for (Question question : quest.getQuestions()) {
            if (question.getId() == id) {
                return question;
            }
        }
        logger.error("Question was not found");
        throw new IllegalArgumentException();
    }
}
