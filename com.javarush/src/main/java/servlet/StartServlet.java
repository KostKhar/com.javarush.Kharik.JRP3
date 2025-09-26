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

@WebServlet(name = "servlet.StartServlet", value = "/start")
public class StartServlet extends HttpServlet {
    private long idQuestion;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        HttpSession session = req.getSession();

        Quest quest = new Quest();
        quest.setQuest(Path.of("quest.json"));
        session.setAttribute("currentQuest", quest);
        this.idQuestion = quest.getId();

        req.setAttribute("name", quest.getName());
        req.setAttribute("description", quest.getDescription());

        req.setAttribute("yes", quest.getStartQuestion().getAnswers().get(0).getAnswerText());
        req.setAttribute("no",  quest.getStartQuestion().getAnswers().get(1).getAnswerText());

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

        if("yes".equals(answer)){
//            Question question = quest.get
            req.setAttribute("name", "Отлично! Ты выбрал Java!");
//            req.setAttribute("yes",);
            req.setAttribute("no", "Учить Hibernate");
            req.getRequestDispatcher("/main.jsp").forward(req, resp);
        } else {
            req.setAttribute("header", "Жаль... Может передумаешь?");
            req.getRequestDispatcher("/fail.jsp").forward(req, resp);
        }
    }
}
