package pe.com.storedev.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String email = request.getParameter("username");

        if (exception.getCause() instanceof EmailNotVerifiedException) {
            response.sendRedirect("/login?emailNotVerified");
        } else if (exception.getCause() instanceof UserInactiveException) {
            response.sendRedirect("/login?inactive=true&email=" + email);
        } else if (exception instanceof BadCredentialsException) {
            response.sendRedirect("/login?invalid=true&email=" + email);
        } else {
            response.sendRedirect("/login?error=true&email=" + email);
        }
    }
}
