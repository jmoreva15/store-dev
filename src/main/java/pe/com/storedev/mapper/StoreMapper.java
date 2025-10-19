package pe.com.storedev.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.storedev.dto.product.AssignProductDTO;
import pe.com.storedev.dto.store.StoreCreateDTO;
import pe.com.storedev.dto.store.StoreDTO;
import pe.com.storedev.dto.store.StoreProductDTO;
import pe.com.storedev.dto.store.StoreUpdateDTO;
import pe.com.storedev.entity.Store;
import pe.com.storedev.entity.StoreProduct;

@Component
@RequiredArgsConstructor
public class StoreMapper {
    private final ProductMapper productMapper;

    public Store toEntity(StoreCreateDTO dto) {
        if (dto == null) return null;

        Store store = new Store();
        store.setRuc(dto.getRuc());
        store.setAddress(dto.getAddress());
        store.setPhone(dto.getPhone());
        store.setActive(dto.getActive());
        return store;
    }

    public Store toEntity(Long id){
        if (id == null) return null;

        Store store = new Store();
        store.setId(id);
        return store;
    }

    public StoreDTO toDTO(Store entity) {
        if (entity == null) return null;

        StoreDTO dto = new StoreDTO();
        dto.setId(entity.getId());
        dto.setRuc(entity.getRuc());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setActive(entity.getActive());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public void updateEntity(Store entity, StoreUpdateDTO dto) {
        if (entity == null || dto == null) return;

        entity.setRuc(dto.getRuc());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setActive(dto.getActive());
    }

    public StoreProductDTO toStoreProductDTO(StoreProduct entity) {
        if (entity == null) return null;

        StoreProductDTO dto = new StoreProductDTO();
        dto.setId(entity.getId());
        dto.setStock(entity.getStock());
        dto.setSalePrice(entity.getSalePrice());
        dto.setPurchasePrice(entity.getPurchasePrice());
        dto.setStore(toDTO(entity.getStore()));
        dto.setProduct(productMapper.toDTO(entity.getProduct()));
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public StoreProduct toAssignedProductEntity(AssignProductDTO dto) {
        if (dto == null) return null;

        StoreProduct entity = new StoreProduct();
        entity.setProduct(productMapper.toEntity(dto.getProductId()));
        entity.setStore(toEntity(dto.getStoreId()));

        return entity;
    }
}
