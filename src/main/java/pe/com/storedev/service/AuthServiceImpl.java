package pe.com.storedev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.com.storedev.dto.auth.ConfirmEmailDTO;
import pe.com.storedev.dto.auth.ResetPasswordDTO;
import pe.com.storedev.entity.Token;
import pe.com.storedev.entity.TokenType;
import pe.com.storedev.entity.User;
import pe.com.storedev.exception.EmailNotVerifiedException;
import pe.com.storedev.exception.ExpiredTokenException;
import pe.com.storedev.exception.InvalidTokenException;
import pe.com.storedev.exception.UserInactiveException;
import pe.com.storedev.repository.TokenRepository;
import pe.com.storedev.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.getActive() || user.getDeleted() || !user.getRole().getActive() || user.getRole().getDeleted()) {
            throw new UserInactiveException("User inactive or deleted: " + email);
        } else if (!user.getEmailVerified()) {
            throw new EmailNotVerifiedException("Email not verified: " + email);
        } else {
            return user;
        }
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

    @Override
    public void confirmEmail(ConfirmEmailDTO dto) {
        Token token = tokenRepository
                .findByValueAndTypeAndActiveTrue(dto.getToken(), TokenType.CONFIRM_EMAIL)
                .orElseThrow(() -> new InvalidTokenException("Token not found"));

        token.setActive(false);
        tokenRepository.save(token);

        if (token.isExpired()) throw new ExpiredTokenException("Token expired");

        User user = token.getUser();
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmailVerified(true);

        userRepository.save(user);
    }

    @Override
    public void resetPassword(ResetPasswordDTO dto) {
        Token token = tokenRepository
                .findByValueAndTypeAndActiveTrue(dto.getToken(), TokenType.RESET_PASSWORD)
                .orElseThrow(() -> new InvalidTokenException("Token not found"));

        token.setActive(false);
        tokenRepository.save(token);

        if (token.isExpired()) throw new ExpiredTokenException("Token expired");

        User user = token.getUser();
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmailVerified(true);

        userRepository.save(user);
    }
}
