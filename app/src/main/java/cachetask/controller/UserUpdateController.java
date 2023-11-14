package cachetask.controller;

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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/users/update")
public class UserUpdateController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserUpdateController.class);
    Gson gson = new Gson();
    private final UserRepository userRepository = new UserApiRepository();
    private final UserService userService = new UserApiService(userRepository);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            BufferedReader reader = req.getReader();
            User updatedUser = gson.fromJson(reader, User.class);

            Long userId = Long.parseLong(req.getParameter("id"));

            boolean updated = userService.update(updatedUser, userId);

            if (updated) {
                PrintWriter out = resp.getWriter();
                out.println(updated);
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

