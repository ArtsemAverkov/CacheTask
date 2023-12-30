package cachetask.filter;

import cachetask.repository.UserApiRepository;
import cachetask.repository.UserRepository;
import cachetask.sevices.UserApiService;
import cachetask.sevices.UserService;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Objects;

@WebFilter(urlPatterns = "/users/read")
public class GetProductFilter implements Filter {
    private final UserRepository userRepository = new UserApiRepository();
    private final UserService userService = new UserApiService(userRepository);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long id = Long.parseLong(request.getParameter("id"));

        try {
                Object read = userService.read(id);
                if (Objects.isNull(read)){
                    response.setContentType("application/json");
                    response.getWriter().write("Ошибка при чтении пользователя");

                }else {
                    chain.doFilter(request, response);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void destroy() {

    }
}
