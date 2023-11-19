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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/users/update")
public class UserUpdateController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(UserUpdateController.class);

    XStream xStream = new XStream();
    JsonParser jsonParser = new JsonParserImpl();
    private final UserRepository userRepository = new UserApiRepository();
    private final UserService userService = new UserApiService(userRepository);
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            BufferedReader reader = req.getReader();
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            String contentType = req.getContentType();
            String requestData = stringBuilder.toString();

            if (contentType != null && contentType.contains("application/json")) {

                User updatedUser = (User) jsonParser.generateObjectFromJson(User.class, requestData);
                Long userId = Long.parseLong(req.getParameter("id"));
                boolean updated = userService.update(updatedUser, userId);
                    if (updated) {
                        resp.setContentType("application/json");
                        PrintWriter out = resp.getWriter();
                        out.println(updated);
                    }
            } else if (contentType != null && contentType.contains("application/xml")) {
                        User userFromXml = (User) xStream.fromXML(requestData);
                        boolean created = userService.create(userFromXml);
                        resp.setContentType("application/xml");
                        PrintWriter out = resp.getWriter();
                        out.println(created);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Пользователь не найден");
            }
        } catch (IOException  e) {
            logger.error("Внутренняя ошибка сервера", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Внутренняя ошибка сервера");
        } catch (NumberFormatException  e){
            logger.error("Неверный формат ID", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Не верный формат Id");
        } catch (IllegalArgumentException e) {
            logger.error("Неверный запрос", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Неверный запрос");
        }

        }
    }

