package pe.com.storedev.service;

import org.springframework.security.core.Authentication;

public interface AuthService {
    void refreshAuthenticatedUser(Authentication authentication);
}
