package cachetask.controller;


import cachetask.entity.User;
import cachetask.repository.UserApiRepository;
import cachetask.repository.UserRepository;
import cachetask.sevices.UserApiService;
import cachetask.sevices.UserService;
import jsonparser.parser.JsonParser;
import jsonparser.parser.JsonParserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users/readAll")
public class UserReadAllController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserReadAllController.class);
    JsonParser jsonParser = new JsonParserImpl();
    private final UserRepository userRepository = new UserApiRepository();
    private final UserService userService = new UserApiService(userRepository);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            List<User> userList = userService.readAll();

            for(User s: userList){
                String json1 = jsonParser.generateJson(s);
                stringBuilder.append(json1);
            }
            resp.setContentType("application/json");
            resp.getWriter().write(String.valueOf(stringBuilder));
        } catch (Exception e) {
            logger.error("Ошибка при чтении всех пользователей", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Внутренняя ошибка сервера");
        }
    }
}
