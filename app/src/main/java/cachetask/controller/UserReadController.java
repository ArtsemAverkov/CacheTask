package cachetask.controller;


import cachetask.entity.User;
import cachetask.repository.UserApiRepository;
import cachetask.repository.UserRepository;
import cachetask.sevices.UserApiService;
import cachetask.sevices.UserService;
import jsonparser.parser.JsonParser;
import jsonparser.parser.JsonParserImpl;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/users/read")
public class UserReadController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(UserReadController.class);

    JsonParser jsonParser = new JsonParserImpl();
    private final UserRepository userRepository = new UserApiRepository();
    private final UserService userService = new UserApiService(userRepository);



    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Long userId = Long.parseLong(req.getParameter("id"));
            User user = userService.read(userId);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonParser.generateJson(user));

        } catch (NumberFormatException e) {
            logger.error("Неверный формат ID", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Неверный формат ID");
        }
    }
}
