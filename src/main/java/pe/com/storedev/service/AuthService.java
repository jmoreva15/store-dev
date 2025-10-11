package pe.com.storedev.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    void refreshAuthenticatedUser(Authentication authentication);
}
