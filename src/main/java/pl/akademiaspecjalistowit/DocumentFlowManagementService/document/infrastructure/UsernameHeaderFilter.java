package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.infrastructure;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.user.entity.UserEntity;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.user.repository.UserRepository;

import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
public class UsernameHeaderFilter implements Filter {

    private final UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String username = httpRequest.getHeader("username");
        Optional<UserEntity> foundUser = userRepository.findByUsername(username);

        if (foundUser.isEmpty()) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing 'username' header.");
            return;
        }

        chain.doFilter(request, response);
    }
}
