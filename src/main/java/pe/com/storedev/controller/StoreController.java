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
import pe.com.storedev.dto.product.AssignProductDTO;
import pe.com.storedev.dto.store.StoreCreateDTO;
import pe.com.storedev.dto.store.StoreDTO;
import pe.com.storedev.dto.store.StoreUpdateDTO;
import pe.com.storedev.service.ProductService;
import pe.com.storedev.service.StoreService;

@Controller
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final ProductService productService;

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_STORE')")
    public String index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        Model model) {
        Page<StoreDTO> stores = storeService.findAll(PageRequest.of(page, size));
        model.addAttribute("stores", stores);
        return "app/store/index";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_STORE')")
    public String create(Model model) {
        model.addAttribute("store", new StoreCreateDTO());
        return "app/store/create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_STORE')")
    public String create(@Valid @ModelAttribute("store") StoreCreateDTO dto,
                         BindingResult result) {
        if (result.hasErrors()) {
            return "app/store/create";
        }

        storeService.create(dto);
        return "redirect:/stores";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('EDIT_STORE')")
    public String edit(@PathVariable Long id, Model model) {
        StoreDTO store = storeService.findById(id);

        StoreUpdateDTO updateDTO = new StoreUpdateDTO();
        updateDTO.setRuc(store.getRuc());
        updateDTO.setAddress(store.getAddress());
        updateDTO.setPhone(store.getPhone());
        updateDTO.setActive(store.getActive());

        model.addAttribute("storeId", id);
        model.addAttribute("store", updateDTO);
        return "app/store/edit";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('EDIT_STORE')")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("store") StoreUpdateDTO dto,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("storeId", id);
            return "app/store/edit";
        }

        storeService.update(id, dto);
        return "redirect:/stores";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('DELETE_STORE')")
    public String delete(@PathVariable Long id) {
        storeService.delete(id);
        return "redirect:/stores";
    }


    @GetMapping("assign-product")
    @PreAuthorize("hasAuthority('ASSIGN_PRODUCT_STORE')")
    public String assignProduct(Model model) {
        model.addAttribute("stores", storeService.findAllActive());
        model.addAttribute("assignProduct", new AssignProductDTO());
        model.addAttribute("products", productService.findAllActive());
        model.addAttribute("allProductAssignments", storeService.findAllProductAssignments());

        return "app/store/assign-product";
    }

    @PostMapping("assign-product")
    @PreAuthorize("hasAuthority('ASSIGN_PRODUCT_STORE')")
    public String assignProduct(@Valid @ModelAttribute("assignProduct") AssignProductDTO dto,
                                BindingResult result,
                                Model model)  {
        if (result.hasErrors()) {
            model.addAttribute("stores", storeService.findAllActive());
            model.addAttribute("products", productService.findAllActive());
            model.addAttribute("allProductAssignments", storeService.findAllProductAssignments());
            return "app/store/assign-product";
        }

        storeService.assignProduct(dto);
        return "redirect:/stores/assign-product";
    }
}
