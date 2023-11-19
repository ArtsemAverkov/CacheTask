package cachetask.controller;


import cachetask.entity.User;
import cachetask.repository.UserApiRepository;
import cachetask.repository.UserRepository;
import cachetask.sevices.UserApiService;
import cachetask.sevices.UserService;
import com.thoughtworks.xstream.XStream;
import jsonparser.parser.JsonParser;
import jsonparser.parser.JsonParserImpl;
import lombok.SneakyThrows;
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
    XStream xStream = new XStream();
    JsonParser jsonParser = new JsonParserImpl();
    private final UserRepository userRepository = new UserApiRepository();
    private final UserService userService = new UserApiService(userRepository);

    @SneakyThrows
    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            BufferedReader reader = req.getReader();
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String requestData = stringBuilder.toString();

            String contentType = req.getContentType();
            if (contentType != null && contentType.contains("application/json")) {
                User user = (User) jsonParser.generateObjectFromJson(User.class, requestData);
                boolean created = userService.create(user);

                resp.setContentType("application/json");
                PrintWriter out = resp.getWriter();
                out.println(created);
            } else if (contentType != null && contentType.contains("application/xml")) {
                User userFromXml = (User) xStream.fromXML(requestData);
                boolean created = userService.create(userFromXml);
                resp.setContentType("application/xml");
                PrintWriter out = resp.getWriter();
                out.println(created);
            } else {
                // Неподдерживаемый тип данных
                resp.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
                resp.getWriter().write("Неподдерживаемый тип данных");
            }
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

