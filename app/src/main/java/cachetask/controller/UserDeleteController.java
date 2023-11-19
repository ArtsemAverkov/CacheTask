package cachetask.controller;

import cachetask.repository.UserApiRepository;
import cachetask.repository.UserRepository;
import cachetask.sevices.UserApiService;
import cachetask.sevices.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/users/delete")
public class UserDeleteController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger( UserDeleteController.class);
    private final UserRepository userRepository = new UserApiRepository();
    private final UserService userService = new UserApiService(userRepository);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            boolean delete = userService.delete(id);
            PrintWriter out = resp.getWriter();
            out.println(delete);
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
