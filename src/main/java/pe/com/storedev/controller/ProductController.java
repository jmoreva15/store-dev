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
import pe.com.storedev.dto.category.CategoryDTO;
import pe.com.storedev.dto.product.ProductCreateDTO;
import pe.com.storedev.dto.product.ProductDTO;
import pe.com.storedev.dto.product.ProductUpdateDTO;
import pe.com.storedev.service.CategoryService;
import pe.com.storedev.service.ProductService;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_PRODUCT')")
    public String index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        Model model) {
        Page<ProductDTO> products = productService.findAll(PageRequest.of(page, size));
        model.addAttribute("products", products);
        return "app/product/index";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_PRODUCT')")
    public String create(Model model) {
        model.addAttribute("product", new ProductCreateDTO());
        model.addAttribute("categories", categoryService.findAll());
        return "app/product/create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_PRODUCT')")
    public String create(@Valid @ModelAttribute("product") ProductCreateDTO dto,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "app/product/create";
        }

        productService.create(dto);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('EDIT_PRODUCT')")
    public String update(@PathVariable Long id, Model model) {
        ProductDTO product = productService.findById(id);

        ProductUpdateDTO updateDTO = new ProductUpdateDTO();
        updateDTO.setName(product.getName());
        updateDTO.setDescription(product.getDescription());
        updateDTO.setActive(product.getActive());
        updateDTO.setCategoryIds(product.getCategories().stream()
                .map(CategoryDTO::getId)
                .collect(Collectors.toSet()));

        model.addAttribute("productId", id);
        model.addAttribute("product", updateDTO);
        model.addAttribute("categories", categoryService.findAll());
        return "app/product/edit";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('EDIT_PRODUCT')")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("product") ProductUpdateDTO dto,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("productId", id);
            model.addAttribute("categories", categoryService.findAll());
            return "app/product/edit";
        }

        productService.update(id, dto);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('DELETE_PRODUCT')")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products";
    }
}
