package servlets;

import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/helloPage")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Перевірка на те, чи залогінений вже користувач. Якщо так, то дати йому доступ до сторінки привітання,
        //в протилежному випадку перенаправити на сторінку входу
        User userSession = (User)req.getSession().getAttribute("User");
        if(userSession == null){
            String path = req.getContextPath() + "/";
            resp.sendRedirect(path);
            return;
        }
        req.setAttribute("path", req.getContextPath());
        req.getRequestDispatcher("HelloPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Виконується log out користувача за допомогою встановлення значення null параметру "User" сесії
        //і користувача буде перенаправлено на сторінку входу
        req.getSession().setAttribute("User", null);
        String path = req.getContextPath() + "/";
        resp.sendRedirect(path);
    }
}
