package pe.com.storedev.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.storedev.dto.category.CategoryCreateDTO;
import pe.com.storedev.dto.category.CategoryDTO;
import pe.com.storedev.dto.category.CategoryUpdateDTO;

import java.util.List;

public interface CategoryService {
    Page<CategoryDTO> findAll(Pageable pageable);
    List<CategoryDTO> findAll();
    CategoryDTO create(CategoryCreateDTO categoryCreateDTO);
    CategoryDTO update(Long id, CategoryUpdateDTO categoryUpdateDTO);
    CategoryDTO findById(Long id);
    void delete(Long id);
}
