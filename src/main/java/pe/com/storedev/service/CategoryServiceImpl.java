package pe.com.storedev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.storedev.dto.category.CategoryCreateDTO;
import pe.com.storedev.dto.category.CategoryDTO;
import pe.com.storedev.dto.category.CategoryUpdateDTO;
import pe.com.storedev.entity.Category;
import pe.com.storedev.exception.NotFoundException;
import pe.com.storedev.mapper.CategoryMapper;
import pe.com.storedev.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        return categoryRepository.findAllNotDeleted(pageable)
                .map(categoryMapper::toDTO);
    }

    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAllNotDeleted()
                .stream().map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO create(CategoryCreateDTO categoryCreateDTO) {
        Category category = categoryMapper.toEntity(categoryCreateDTO);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toDTO(saved);
    }

    @Override
    public CategoryDTO update(Long id, CategoryUpdateDTO categoryUpdateDTO) {
        Category existing = categoryRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + id));

        categoryMapper.updateEntity(existing, categoryUpdateDTO);
        Category updated = categoryRepository.save(existing);
        return categoryMapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + id));
        return categoryMapper.toDTO(category);
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + id));

        category.setDeleted(Boolean.TRUE);
        categoryRepository.save(category);
    }
}
