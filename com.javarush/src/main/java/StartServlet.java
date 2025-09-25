import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.file.Path;

@WebServlet(name = "StartServlet", value = "/")
public class StartServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        HttpSession session = req.getSession();

        Quest quest = new Quest();
        quest.setQuest(Path.of("src/main/resources/quest.json"));
        session.setAttribute("currentQuest", quest);

        req.setAttribute("header", "Привет, Амиго! Хочешь стать программистом?");
        req.setAttribute("yes", "Да!");
        req.setAttribute("no", "Нет");

        req.getRequestDispatcher("/start.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        String answer = req.getParameter("answer");

        // Проверяем, что ответ присутствует
        if(answer == null || answer.trim().isEmpty()) {
            req.setAttribute("error", "Пожалуйста, выберите ответ");
            req.getRequestDispatcher("/start.jsp").forward(req, resp);
            return;
        }

        // Обрабатываем ответ
        if("yes".equals(answer)){
            req.setAttribute("header", "Отлично! Ты выбрал Java!");
            req.setAttribute("answer1", "Учить Spring");
            req.setAttribute("answer2", "Учить Hibernate");
            req.getRequestDispatcher("/next.jsp").forward(req, resp);
        } else {
            req.setAttribute("header", "Жаль... Может передумаешь?");
            req.getRequestDispatcher("/fail.jsp").forward(req, resp);
        }
    }
}
