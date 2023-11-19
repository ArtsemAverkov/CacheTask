package cachetask.controller;

import cachetask.aop.cache.CacheFactory;
import cachetask.aop.cache.CachingAspect;
import cachetask.entity.User;
import cachetask.repository.UserApiRepository;
import cachetask.repository.UserRepository;
import cachetask.sevices.UserApiService;
import cachetask.sevices.UserService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/users/create")
public class UserCreateController extends HttpServlet {


    private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);
    Gson gson = new Gson();
    private final UserRepository userRepository = new UserApiRepository();
    private final UserService userService = new UserApiService(userRepository);

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.setContentType("application/json");
            BufferedReader reader = req.getReader();
            User user = gson.fromJson(reader, User.class);
            boolean b = userService.create(user);

            PrintWriter out = resp.getWriter();
            out.println(b);
        } catch (NumberFormatException e) {
            logger.error("Неверный формат ID", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Неверный формат ID");
        } catch (Exception e) {
            logger.error("Ошибка при обработке запроса", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Внутренняя ошибка сервера");
        }
    }
}

