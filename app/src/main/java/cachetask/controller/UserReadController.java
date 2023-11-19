package cachetask.controller;

import cachetask.aop.cache.CacheFactory;
import cachetask.aop.cache.Cacheable;
import cachetask.aop.cache.CachingAspect;
import cachetask.entity.User;
import cachetask.repository.UserApiRepository;
import cachetask.repository.UserRepository;
import cachetask.sevices.UserApiService;
import cachetask.sevices.UserService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/read")
public class UserReadController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(UserReadController.class);
    Gson gson = new Gson();
    private final UserRepository userRepository = new UserApiRepository();
    private final UserService userService = new UserApiService(userRepository);



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long userId = Long.parseLong(req.getParameter("id"));
            User user = userService.read(userId);
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(user));
        } catch (NumberFormatException e) {
            logger.error("Неверный формат ID", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Неверный формат ID");
        }
    }
}
