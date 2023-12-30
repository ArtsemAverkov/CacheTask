package cachetask.controller;

import cachetask.entity.User;
import cachetask.pdf.PdfGenerate;
import cachetask.pdf.PdfGenerateImpl;
import cachetask.repository.UserApiRepository;
import cachetask.repository.UserRepository;
import cachetask.sevices.UserApiService;
import cachetask.sevices.UserService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/users/read_pdf")
public class UserReadPdfController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(UserReadController.class);

    PdfGenerate pdfGenerate = new PdfGenerateImpl();
    private final UserRepository userRepository = new UserApiRepository();
    private final UserService userService = new UserApiService(userRepository);

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long userId = Long.parseLong(req.getParameter("id"));
            User user = userService.read(userId);
            pdfGenerate.writeToPdfFile(user);

            File file = new File("updated_Clevertec_Template.pdf");
            FileInputStream inputStream = new FileInputStream(file);

            resp.setContentType("application/pdf");
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

            OutputStream outStream = resp.getOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            outStream.flush();
        } catch (NumberFormatException e) {
            logger.error("Неверный формат ID", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Неверный формат ID");
        }
    }
}
