package pe.com.storedev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.com.storedev.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements UserDetailsService, AuthService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(""));
    }

    @Override
    public void refreshAuthenticatedUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) return;

        String email = authentication.getName();
        UserDetails updatedUser = loadUserByUsername(email);
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedUser,
                authentication.getCredentials(),
                updatedUser.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
