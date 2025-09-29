package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quest.Answer;
import quest.Quest;
import quest.Question;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "StartServlet", value = "/start")
public class StartServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        HttpSession session = req.getSession();

        // Создаем новый квест или получаем существующий из сессии
        Quest quest = (Quest) session.getAttribute("currentQuest");
        if (quest == null) {
            quest = new Quest();
            quest.setQuest(Path.of("quest.json"));
            session.setAttribute("currentQuest", quest);
        }

        Question currentQuestion = quest.getCurrentQuestion();
        session.setAttribute("currentQuestion", currentQuestion);

        req.setAttribute("name", quest.getName());
        req.setAttribute("description", quest.getDescription());
        req.setAttribute("question", currentQuestion.getQuestionText());

        List<Answer> answers = currentQuestion.getAnswers();
        req.setAttribute("yes", answers.get(0).getAnswerText());
        req.setAttribute("no",   answers.get(1).getAnswerText());

        req.getRequestDispatcher("/main.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        String answer = req.getParameter("answer");

        Quest quest = (Quest) session.getAttribute("currentQuest");
        Question currentQuestion = (Question) session.getAttribute("currentQuestion");

//        if (quest == null || currentQuestion == null) {
//            resp.sendRedirect(req.getContextPath() + "/start");
//            return;
//        }

        Question nextQuestion = null;

        if(answer.equals("yes")){
            int id =  currentQuestion.getAnswers().get(0).getNextQuestionId();
            if (id == quest.getQuestions().size()-1) {
                req.getRequestDispatcher("/win.jsp").forward(req, resp);
            }
            nextQuestion = quest.getQuestions().get(id);
        } else {
            nextQuestion = quest.getQuestions().get(currentQuestion.getAnswers().get(1).getNextQuestionId());
        }

        if (nextQuestion != null) {
            // Обновляем текущий вопрос
            quest.setCurrentQuestion(nextQuestion);
            session.setAttribute("currentQuestion", nextQuestion);

            // Показываем следующую страницу с вопросом
            showCurrentQuestion(req, resp, nextQuestion, quest);

        } else {
            // Конец квеста
            session.removeAttribute("currentQuest");
            session.removeAttribute("currentQuestion");

            req.setAttribute("name", quest.getName());
            req.setAttribute("description", quest.getDescription());

            if (answer.equals("yes")) {
                req.getRequestDispatcher("/main.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/fail.jsp").forward(req, resp);
            }
        }
    }
    // Вспомогательный метод для показа вопроса
    private void showCurrentQuestion(HttpServletRequest req, HttpServletResponse resp,
                                     Question question, Quest quest)
            throws ServletException, IOException {

        req.setAttribute("name", quest.getName());
        req.setAttribute("description", quest.getDescription());
        req.setAttribute("question", question.getQuestionText());

        List<Answer> answers = question.getAnswers();
        if (answers.size() >= 2) {
            req.setAttribute("yes", answers.get(0).getAnswerText());
            req.setAttribute("no", answers.get(1).getAnswerText());
        }

        req.getRequestDispatcher("/main.jsp").forward(req, resp);
    }
}
