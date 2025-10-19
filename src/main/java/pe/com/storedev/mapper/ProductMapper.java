package pe.com.storedev.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.storedev.dto.product.ProductCreateDTO;
import pe.com.storedev.dto.product.ProductDTO;
import pe.com.storedev.dto.product.ProductUpdateDTO;
import pe.com.storedev.entity.Product;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final CategoryMapper categoryMapper;

    public Product toEntity(ProductCreateDTO dto) {
        if (dto == null) return null;

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setActive(dto.getActive());
        product.setCategories(categoryMapper.toEntitySet(dto.getCategoryIds()));

        return product;
    }

    public Product toEntity(Long id) {
        if (id == null) return null;

        Product product = new Product();
        product.setId(id);
        return product;
    }

    public ProductDTO toDTO(Product entity) {
        if (entity == null) return null;

        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setActive(entity.getActive());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        dto.setCategories(entity.getCategories().stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toSet()));

        return dto;
    }

    public void updateEntity(Product entity, ProductUpdateDTO dto) {
        if (entity == null || dto == null) return;

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setActive(dto.getActive());
        entity.setCategories(categoryMapper.toEntitySet(dto.getCategoryIds()));
    }
}
