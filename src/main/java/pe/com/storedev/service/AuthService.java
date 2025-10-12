package pe.com.storedev.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import pe.com.storedev.dto.auth.ConfirmEmailDTO;

public interface AuthService extends UserDetailsService {
    void refreshAuthenticatedUser(Authentication authentication);
    void confirmEmail(ConfirmEmailDTO confirmEmailDTO);
}
