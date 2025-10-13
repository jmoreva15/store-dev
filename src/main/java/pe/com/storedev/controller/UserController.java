package pe.com.storedev.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.com.storedev.dto.user.UserCreateDTO;
import pe.com.storedev.dto.user.UserDTO;
import pe.com.storedev.dto.user.UserUpdateDTO;
import pe.com.storedev.service.EmailService;
import pe.com.storedev.service.RoleService;
import pe.com.storedev.service.UserService;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final EmailService emailService;

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_USER')")
    public String index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        Model model) {
        Page<UserDTO> users = userService.findAll(PageRequest.of(page, size));
        model.addAttribute("users", users);
        return "app/user/index";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_USER')")
    public String create(Model model) {
        model.addAttribute("user", new UserCreateDTO());
        model.addAttribute("roles", roleService.findAllActive());
        return "app/user/create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_USER')")
    public String create(@Valid @ModelAttribute("user") UserCreateDTO dto,
                         BindingResult result,
                         Model model) throws MessagingException {
        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.findAllActive());
            return "app/user/create";
        }

        emailService.sendConfirmEmail(userService.create(dto).getId());
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('EDIT_USER')")
    public String edit(@PathVariable Long id, Model model) {
        UserDTO user = userService.findById(id);

        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setName(user.getName());
        updateDTO.setEmail(user.getEmail());
        updateDTO.setActive(user.getActive());
        updateDTO.setRoleId(user.getRole().getId());

        model.addAttribute("userId", id);
        model.addAttribute("user", updateDTO);
        model.addAttribute("roles", roleService.findAllActive());
        return "app/user/edit";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('EDIT_USER')")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("user") UserUpdateDTO dto,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userId", id);
            model.addAttribute("roles", roleService.findAllActive());
            return "app/user/edit";
        }

        userService.update(id, dto);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/users";
    }

    @GetMapping("/send-confirm-email/{userId}")
    @PreAuthorize("hasAuthority('SEND_CONFIRM_EMAIL')")
    public String sendConfirmEmail(@PathVariable Long userId) throws MessagingException {
        emailService.sendConfirmEmail(userId);
        return "redirect:/users";
    }
}
