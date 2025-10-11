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
import pe.com.storedev.dto.supplier.SupplierCreateDTO;
import pe.com.storedev.dto.supplier.SupplierDTO;
import pe.com.storedev.dto.supplier.SupplierUpdateDTO;
import pe.com.storedev.service.SupplierService;

@Controller
@RequestMapping("/suppliers")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_SUPPLIER')")
    public String index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        Model model) {
        Page<SupplierDTO> suppliers = supplierService.findAll(PageRequest.of(page, size));
        model.addAttribute("suppliers", suppliers);
        return "app/supplier/index";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_SUPPLIER')")
    public String create(Model model) {
        model.addAttribute("supplier", new SupplierCreateDTO());
        return "app/supplier/create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_SUPPLIER')")
    public String create(@Valid @ModelAttribute("supplier") SupplierCreateDTO dto,
                         BindingResult result) {
        if (result.hasErrors()) {
            return "app/supplier/create";
        }

        supplierService.create(dto);
        return "redirect:/suppliers";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('EDIT_SUPPLIER')")
    public String update(@PathVariable Long id, Model model) {
        SupplierDTO supplier = supplierService.findById(id);

        SupplierUpdateDTO updateDTO = new SupplierUpdateDTO();
        updateDTO.setRuc(supplier.getRuc());
        updateDTO.setName(supplier.getName());
        updateDTO.setEmail(supplier.getEmail());
        updateDTO.setAddress(supplier.getAddress());
        updateDTO.setPhone(supplier.getPhone());
        updateDTO.setActive(supplier.getActive());

        model.addAttribute("supplierId", id);
        model.addAttribute("supplier", updateDTO);
        return "app/supplier/edit";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('EDIT_SUPPLIER')")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("supplier") SupplierUpdateDTO dto,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("supplierId", id);
            return "app/supplier/edit";
        }

        supplierService.update(id, dto);
        return "redirect:/suppliers";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('DELETE_SUPPLIER')")
    public String delete(@PathVariable Long id) {
        supplierService.delete(id);
        return "redirect:/suppliers";
    }
}
