package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quest.Quest;
import quest.Question;

import java.io.IOException;
import java.nio.file.Path;

@WebServlet(name = "StartServlet", value = "/start")
public class StartServlet extends HttpServlet {
    private Quest quest = new Quest();
    private Question currentQuestion;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        HttpSession session = req.getSession();

        this.quest.setQuest(Path.of("quest.json"));
        session.setAttribute("currentQuest", quest);
        currentQuestion = quest.getCurrentQuestion();

        req.setAttribute("name", quest.getName());
        req.setAttribute("description", quest.getDescription());

        req.setAttribute("question", quest.getCurrentQuestion().getQuestionText());
        req.setAttribute("yes", quest.getCurrentQuestion().getAnswers().get(0).getAnswerText());
        req.setAttribute("no",  quest.getCurrentQuestion().getAnswers().get(1).getAnswerText());

        req.getRequestDispatcher("/main.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        String answer = req.getParameter("answer");
//
//        // Проверяем, что ответ присутствует
//        if(answer == null || answer.trim().isEmpty()) {
//            req.setAttribute("error", "Пожалуйста, выберите ответ");
//            req.getRequestDispatcher("/main.jsp").forward(req, resp);
//            return;
//        }

        if(answer.equals(quest.getCurrentQuestion().getAnswers().get(0).getAnswerText())){
            currentQuestion = currentQuestion.getAnswers().get(0).getNextQuestion();

            req.setAttribute("question", quest.getCurrentQuestion().getQuestionText());
            req.setAttribute("yes", quest.getCurrentQuestion().getAnswers().get(0).getAnswerText());
            req.setAttribute("no",  quest.getCurrentQuestion().getAnswers().get(1).getAnswerText());

            req.getRequestDispatcher("/main.jsp").forward(req, resp);
        } else {
            currentQuestion = currentQuestion.getAnswers().get(1).getNextQuestion();

            req.setAttribute("question", quest.getCurrentQuestion().getQuestionText());
            req.setAttribute("yes", quest.getCurrentQuestion().getAnswers().get(0).getAnswerText());
            req.setAttribute("no",  quest.getCurrentQuestion().getAnswers().get(1).getAnswerText());

            req.getRequestDispatcher("/fail.jsp").forward(req, resp);
        }
    }
}
