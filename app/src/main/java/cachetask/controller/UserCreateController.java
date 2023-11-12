package cachetask.controller;

import cachetask.entity.User;
import cachetask.repository.UserApiRepository;
import cachetask.repository.UserRepository;
import cachetask.sevices.UserApiService;
import cachetask.sevices.UserService;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/users/create")
public class UserCreateController extends HttpServlet {
    Gson gson = new Gson();
    private final UserRepository userRepository = new UserApiRepository();
    private final UserService userService = new UserApiService(userRepository);

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        BufferedReader reader = req.getReader();
        StringBuilder jsonInput = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonInput.append(line);
        }
        String jsonData = jsonInput.toString();


        User user = gson.fromJson(jsonData, User.class);
        boolean b = userService.create(user);

        PrintWriter out = resp.getWriter();
        out.println(b);
    }
}

