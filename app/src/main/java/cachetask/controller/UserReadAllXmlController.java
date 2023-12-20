package cachetask.controller;

import cachetask.entity.User;
import cachetask.repository.UserApiRepository;
import cachetask.repository.UserRepository;
import cachetask.sevices.UserApiService;
import cachetask.sevices.UserService;
import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users/readAllXml")
public class UserReadAllXmlController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(UserReadAllXmlController.class);
    XStream xStream = new XStream();
    private final UserRepository userRepository = new UserApiRepository();
    private final UserService userService = new UserApiService(userRepository);
    private static final int DEFAULT_PAGE_SIZE = 20;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int page = 1;
            int pageSize = DEFAULT_PAGE_SIZE;

            if (req.getParameter("page") != null) {
                page = Integer.parseInt(req.getParameter("page"));
            }

            if (req.getParameter("pageSize") != null) {
                pageSize = Integer.parseInt(req.getParameter("pageSize"));
            }

            List<User> userList = userService.readAll(page, pageSize);
            String xml = xStream.toXML(userList);
            resp.setContentType("application/json");
            resp.getWriter().write(xml);
        } catch (Exception e) {
            logger.error("Ошибка при чтении всех пользователей", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Внутренняя ошибка сервера");
        }
    }
}