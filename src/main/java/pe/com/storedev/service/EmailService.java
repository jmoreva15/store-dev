package pe.com.storedev.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendConfirmEmail(Long userId) throws MessagingException;
    void sendResetPasswordEmail(String email) throws MessagingException;
}
