import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "StartServlet", value = "/")
public class StartServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        getServletContext().getRequestDispatcher("/start.jsp").forward(req, resp);

        HttpSession session =  req.getSession();

        Quest quest = new Quest();
        req.setAttribute("header", "Привет, Амиго! Хочешь стать программистом?");
        req.setAttribute("answer1", "да!");
        req.setAttribute("answer2", "нет");

        String answer1 = req.getParameter("answer1");
        String answer2 = req.getParameter("answer1");

        if(answer1 == null || answer2 == null) {
            req.setAttribute("error", "Друг, выбери вариант ответа");
            return;
        }

        if(!answer1.isEmpty()){
            getServletContext().getRequestDispatcher("/next.jsp").forward(req, resp);
        } else {
            getServletContext().getRequestDispatcher("/fail.jsp").forward(req, resp);
        }

    }
}
