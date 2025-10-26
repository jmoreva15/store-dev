package pe.com.storedev.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.storedev.dto.product.AssignProductDTO;
import pe.com.storedev.dto.store.StoreProductDTO;
import pe.com.storedev.entity.StoreProduct;

@Component
@RequiredArgsConstructor
public class StoreProductMapper {
    private final ProductMapper productMapper;
    private final StoreMapper storeMapper;

    public StoreProductDTO toDTO(StoreProduct entity) {
        if (entity == null) return null;

        StoreProductDTO dto = new StoreProductDTO();
        dto.setId(entity.getId());
        dto.setStock(entity.getStock());
        dto.setSalePrice(entity.getSalePrice());
        dto.setPurchasePrice(entity.getPurchasePrice());
        dto.setStore(storeMapper.toDTO(entity.getStore()));
        dto.setProduct(productMapper.toDTO(entity.getProduct()));
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public StoreProduct toDTO(Long id) {
        if (id == null) return null;

        StoreProduct dto = new StoreProduct();
        dto.setId(id);
        return dto;
    }


    public StoreProduct toAssignedProductEntity(AssignProductDTO dto) {
        if (dto == null) return null;

        StoreProduct entity = new StoreProduct();
        entity.setProduct(productMapper.toEntity(dto.getProductId()));
        entity.setStore(storeMapper.toEntity(dto.getStoreId()));

        return entity;
    }
}
