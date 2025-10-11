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
import pe.com.storedev.dto.category.CategoryCreateDTO;
import pe.com.storedev.dto.category.CategoryDTO;
import pe.com.storedev.dto.category.CategoryUpdateDTO;
import pe.com.storedev.service.CategoryService;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_CATEGORY')")
    public String index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        Model model) {
        Page<CategoryDTO> categories = categoryService.findAll(PageRequest.of(page, size));
        model.addAttribute("categories", categories);
        return "app/category/index";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_CATEGORY')")
    public String create(Model model) {
        model.addAttribute("category", new CategoryCreateDTO());
        return "app/category/create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_CATEGORY')")
    public String create(@Valid @ModelAttribute("category") CategoryCreateDTO dto,
                         BindingResult result) {
        if (result.hasErrors()) {
            return "app/category/create";
        }

        categoryService.create(dto);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('EDIT_CATEGORY')")
    public String update(@PathVariable Long id, Model model) {
        CategoryDTO category = categoryService.findById(id);

        CategoryUpdateDTO updateDTO = new CategoryUpdateDTO();
        updateDTO.setName(category.getName());
        updateDTO.setDescription(category.getDescription());

        model.addAttribute("categoryId", id);
        model.addAttribute("category", updateDTO);
        return "app/category/edit";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('EDIT_CATEGORY')")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("category") CategoryUpdateDTO dto,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categoryId", id);
            return "app/category/edit";
        }

        categoryService.update(id, dto);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('DELETE_CATEGORY')")
    public String delete(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/categories";
    }
}
