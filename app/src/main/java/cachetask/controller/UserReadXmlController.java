package cachetask.controller;

import cachetask.entity.User;
import cachetask.repository.UserApiRepository;
import cachetask.repository.UserRepository;
import cachetask.sevices.UserApiService;
import cachetask.sevices.UserService;
import com.thoughtworks.xstream.XStream;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/readXml")
public class UserReadXmlController  extends HttpServlet{

        private static final Logger logger = LoggerFactory.getLogger(UserReadXmlController.class);

        XStream xStream = new XStream();
        private final UserRepository userRepository = new UserApiRepository();
        private final UserService userService = new UserApiService(userRepository);



        @SneakyThrows
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try {
                Long userId = Long.parseLong(req.getParameter("id"));
                User user = userService.read(userId);
                resp.setContentType("application/json");
                resp.getWriter().write(xStream.toXML(user));
            } catch (NumberFormatException e) {
                logger.error("Неверный формат ID", e);
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Неверный формат ID");
            }
        }
    }

