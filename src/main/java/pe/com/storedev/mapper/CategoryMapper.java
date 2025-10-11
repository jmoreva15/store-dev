package pe.com.storedev.mapper;

import org.springframework.stereotype.Component;
import pe.com.storedev.dto.category.CategoryCreateDTO;
import pe.com.storedev.dto.category.CategoryDTO;
import pe.com.storedev.dto.category.CategoryUpdateDTO;
import pe.com.storedev.entity.Category;

@Component
public class CategoryMapper {
    public Category toEntity(CategoryCreateDTO dto) {
        if (dto == null) return null;

        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setDeleted(false);
        return category;
    }

    public CategoryDTO toDTO(Category entity) {
        if (entity == null) return null;

        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public void updateEntity(Category entity, CategoryUpdateDTO dto) {
        if (entity == null || dto == null) return;

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
    }
}
