package pe.com.storedev.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.com.storedev.dto.customer.CustomerCreateDTO;
import pe.com.storedev.dto.customer.CustomerDTO;
import pe.com.storedev.dto.customer.CustomerUpdateDTO;
import pe.com.storedev.entity.DocumentType;
import pe.com.storedev.service.CustomerService;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public String index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        Model model) {
        Page<CustomerDTO> customers = customerService.findAll(PageRequest.of(page, size));
        model.addAttribute("customers", customers);
        return "app/customer/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("customer", new CustomerCreateDTO());
        model.addAttribute("documentTypes", DocumentType.values());
        return "app/customer/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("customer") CustomerCreateDTO dto,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("documentTypes", DocumentType.values());
            return "app/customer/create";
        }

        customerService.create(dto);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String update(@PathVariable Long id, Model model) {
        CustomerDTO customer = customerService.findById(id);

        CustomerUpdateDTO updateDTO = new CustomerUpdateDTO();
        updateDTO.setDocumentType(customer.getDocumentType());
        updateDTO.setDocumentNumber(customer.getDocumentNumber());
        updateDTO.setName(customer.getName());
        updateDTO.setAddress(customer.getAddress());
        updateDTO.setPhone(customer.getPhone());

        model.addAttribute("customerId", id);
        model.addAttribute("customer", updateDTO);
        model.addAttribute("documentTypes", DocumentType.values());
        return "app/customer/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("customer") CustomerUpdateDTO dto,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("documentTypes", DocumentType.values());
            model.addAttribute("customerId", id);
            return "app/customer/edit";
        }

        customerService.update(id, dto);
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        customerService.delete(id);
        return "redirect:/customers";
    }
}
