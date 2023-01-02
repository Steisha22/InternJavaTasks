package servlets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@WebServlet("/")
public class MainServlet extends HttpServlet {

    @Override
    public void init() {
        try {
            //Завантаження інформації про юзерів із json файлу в список
            Reader reader = new BufferedReader(new FileReader(getServletContext().getRealPath("/")+"users.json"));
            List<User> users = new Gson().fromJson(reader, new TypeToken<List<User>>() {}.getType());
            //Список юзерів кладеться у загальнодоступний контекст
            getServletContext().setAttribute("Users", users);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Перевірка на те, чи не залогінений вже користувач. Якщо так, то не давати йому доступ до сторінки входу
        //і перенаправляти на сторінку привітання
        User userSession = (User)req.getSession().getAttribute("User");
        if(userSession != null){
            String path = req.getContextPath() + "/helloPage";
            resp.sendRedirect(path);
            return;
        }
        //Якщо користувач не залогінений, повернути йому сторінку входу
        req.setAttribute("path", req.getContextPath());
        req.getRequestDispatcher("mainPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Отримання списку зареєстрованих користувачів із контексту додатку
        List<User> users = (List<User>) getServletContext().getAttribute("Users");

        //Отримання значень введених логіну (електронна пошта) та паролю
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        //Пошук користувача із списку зареєстрованих спочатку по логіну,
        //а потім, у випадку знаходження відповідного користувача, порівнюється значення паролю.
        for(int i = 0; i < users.size(); i++){
            if(email.equals(users.get(i).getEmail())){
                if(password.equals(users.get(i).getPassword())){
                    //Якщо і логін, і пароль введені вірно, то відповідний об'єкт класу User кладеться в сесію.
                    //Значення параметру "User" в сесії буде перевірятися перед спробою перейти на будь-яку сторінку веб-додатку.
                    req.getSession().setAttribute("User", users.get(i));

                    //Після успішного входу користувач перенаправляється на сторінку привітання
                    String path = req.getContextPath() + "/helloPage";
                    resp.sendRedirect(path);
                    return;
                }
                else{
                    //Якщо пароль не вірний, то повертається сторінка, на який буде червоним підсвічене поле вводу паролю
                    //і буде виведене відповідне повідомлення у placeholder поля
                    req.setAttribute("path", req.getContextPath());
                    req.getRequestDispatcher("mainPageNotCorrectPassword.jsp").forward(req, resp);
                }
            }
            else{
                //Якщо логін не вірний, то повертається сторінка, на який буде червоним підсвічене поле вводу логіну
                //і буде виведене відповідне повідомлення у placeholder поля
                req.setAttribute("path", req.getContextPath());
                req.getRequestDispatcher("mainPageNotCorrectEmail.jsp").forward(req, resp);
            }
        }
    }
}