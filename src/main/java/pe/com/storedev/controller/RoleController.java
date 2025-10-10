package pe.com.storedev.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.com.storedev.dto.rol.RoleCreateDTO;
import pe.com.storedev.dto.rol.RoleDTO;
import pe.com.storedev.dto.rol.RolePermissionsDTO;
import pe.com.storedev.dto.rol.RoleUpdateDTO;
import pe.com.storedev.service.RoleService;

@Controller
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_ROLE')")
    public String index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        Model model) {
        Page<RoleDTO> roles = roleService.findAll(PageRequest.of(page, size));
        model.addAttribute("roles", roles);
        return "app/roles/index";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    public String create(Model model) {
        model.addAttribute("role", new RoleCreateDTO());
        return "app/roles/create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    public String create(@Valid @ModelAttribute("role") RoleCreateDTO dto,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            return "app/roles/create";
        }

        roleService.create(dto);
        return "redirect:/roles";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('EDIT_ROLE')")
    public String edit(@PathVariable Long id, Model model) {
        RoleDTO role = roleService.findById(id);

        RoleUpdateDTO updateDTO = new RoleUpdateDTO();
        updateDTO.setName(role.getName());
        updateDTO.setActive(role.getActive());

        model.addAttribute("roleId", id);
        model.addAttribute("role", updateDTO);
        return "app/roles/edit";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('EDIT_ROLE')")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute("role") RoleUpdateDTO dto,
                       BindingResult result,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roleId", id);
            return "app/roles/edit";
        }

        roleService.update(id, dto);
        return "redirect:/roles";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    public String delete(@PathVariable Long id) {
        roleService.delete(id);
        return "redirect:/roles";
    }

    @GetMapping("/assign-permissions")
    @PreAuthorize("hasAuthority('ASSIGN_PERMISSION')")
    public String assignPermissions(@RequestParam Long roleId, Model model) {
        RolePermissionsDTO role = roleService.findRoleWithAllPermissions(roleId);
        model.addAttribute("role", role);
        model.addAttribute("roles", roleService.findAll());
        return "app/roles/assign-permissions";
    }

    @PostMapping("/assign-permissions/{roleId}/assign-role-permission")
    @PreAuthorize("hasAuthority('ASSIGN_PERMISSION')")
    public String assignRolePermission(@PathVariable Long roleId,
                                   @RequestParam Long permissionId,
                                   @RequestParam Boolean assigned) {

        roleService.assignPermissionToRole(roleId, permissionId, assigned);
        return "redirect:/roles/assign-permissions?roleId=" + roleId;
    }
}
