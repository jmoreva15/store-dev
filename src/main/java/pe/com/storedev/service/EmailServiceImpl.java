package pe.com.storedev.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pe.com.storedev.entity.Token;
import pe.com.storedev.entity.TokenType;
import pe.com.storedev.entity.User;
import pe.com.storedev.exception.NotFoundException;
import pe.com.storedev.repository.TokenRepository;
import pe.com.storedev.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final TemplateEngine templateEngine;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    @Value("${spring.application.url}")
    private String appUrl;

    @Override
    public void sendConfirmEmail(Long userId) throws MessagingException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Token token = new Token();
        token.setUser(user);
        token.setType(TokenType.CONFIRM_EMAIL);
        tokenRepository.save(token);

        String actionUrl = appUrl + "/confirm-email?token=" + token.getValue();

        Context context = new Context();
        context.setVariable("name", user.getName());
        context.setVariable("actionUrl", actionUrl);

        String htmlContent = templateEngine.process("email/confirm-email", context);

        sendEmail(user.getEmail(), "Confirm your email", htmlContent, true);
    }

    @Override
    public void sendResetPasswordEmail(String email) throws MessagingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Token token = new Token();
        token.setUser(user);
        token.setType(TokenType.RESET_PASSWORD);
        tokenRepository.save(token);

        String actionUrl = appUrl + "/reset-password?token=" + token.getValue();

        Context context = new Context();
        context.setVariable("name", user.getName());
        context.setVariable("actionUrl", actionUrl);

        String htmlContent = templateEngine.process("email/reset-password", context);

        sendEmail(user.getEmail(), "Reset your password", htmlContent, true);
    }

    private void sendEmail(String to, String subject, String content, boolean isHtml)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, isHtml);
        mailSender.send(message);
    }
}
