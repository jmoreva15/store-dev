package pe.com.storedev.mapper;

import org.springframework.stereotype.Component;
import pe.com.storedev.dto.store.StoreCreateDTO;
import pe.com.storedev.dto.store.StoreDTO;
import pe.com.storedev.dto.store.StoreUpdateDTO;
import pe.com.storedev.entity.Store;

@Component
public class StoreMapper {
    public Store toEntity(StoreCreateDTO dto) {
        if (dto == null) return null;

        Store store = new Store();
        store.setRuc(dto.getRuc());
        store.setAddress(dto.getAddress());
        store.setPhone(dto.getPhone());
        store.setActive(dto.getActive() != null ? dto.getActive() : Boolean.TRUE);
        store.setDeleted(Boolean.FALSE);
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
}
