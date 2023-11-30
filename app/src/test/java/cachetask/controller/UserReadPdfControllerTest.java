package cachetask.controller;

import cachetask.entity.User;
import cachetask.extension.discount.ValidParameterResolverUser;

import cachetask.sevices.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@ExtendWith(ValidParameterResolverUser.class)
public class UserReadPdfControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserReadPdfController controller;

    @Test
    void testDoGetWithValidUserId(User user) throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        controller.doGet(request, response);
        verify(userService, times(1)).read(1L);

    }

}


