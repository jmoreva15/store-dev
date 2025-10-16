package pe.com.storedev.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.com.storedev.dto.auth.ConfirmEmailDTO;
import pe.com.storedev.dto.auth.ForgotPasswordDTO;
import pe.com.storedev.dto.auth.ResetPasswordDTO;
import pe.com.storedev.service.AuthService;
import pe.com.storedev.service.EmailService;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final EmailService emailService;

    @GetMapping("/login")
    public String login() {
        return "app/auth/login";
    }

    @GetMapping("/confirm-email")
    public String confirmEmail(@RequestParam(value = "token", required = false) String token, Model model) {
        ConfirmEmailDTO dto = new ConfirmEmailDTO();
        dto.setToken(token);
        model.addAttribute("confirmEmailDTO", dto);
        return "app/auth/confirm-email";
    }

    @PostMapping("/confirm-email")
    public String confirmEmail(@Valid @ModelAttribute("confirmEmailDTO") ConfirmEmailDTO dto,
            BindingResult result) {
        if (result.hasErrors()) {
            return "app/auth/confirm-email";
        }

        authService.confirmEmail(dto);
        return "redirect:/login?confirmEmail";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("forgotPasswordDTO", new ForgotPasswordDTO());
        return "app/auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@Valid @ModelAttribute("forgotPasswordDTO") ForgotPasswordDTO dto,
                                 BindingResult result) throws MessagingException {
        if (result.hasErrors()) {
            return "app/auth/forgot-password";
        }

        emailService.sendResetPasswordEmail(dto.getEmail());
        return "redirect:/login?forgotPassword";
    }

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam(value = "token", required = false) String token,
                                Model model) {
        ResetPasswordDTO dto = new ResetPasswordDTO();
        dto.setToken(token);
        model.addAttribute("resetPasswordDTO", dto);
        return "app/auth/reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@Valid @ModelAttribute("resetPasswordDTO") ResetPasswordDTO dto,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "app/auth/reset-password";
        }

        authService.resetPassword(dto);
        return "redirect:/login?resetPassword";
    }
}
