package pe.com.storedev.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.com.storedev.dto.auth.ConfirmEmailDTO;
import pe.com.storedev.service.AuthService;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

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
}
