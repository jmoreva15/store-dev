package pe.com.storedev.mapper;

import org.springframework.stereotype.Component;
import pe.com.storedev.dto.supplier.SupplierCreateDTO;
import pe.com.storedev.dto.supplier.SupplierDTO;
import pe.com.storedev.dto.supplier.SupplierUpdateDTO;
import pe.com.storedev.entity.Supplier;

@Component
public class SupplierMapper {
    public Supplier toEntity(SupplierCreateDTO dto) {
        if (dto == null) return null;

        Supplier supplier = new Supplier();
        supplier.setRuc(dto.getRuc());
        supplier.setName(dto.getName());
        supplier.setEmail(dto.getEmail());
        supplier.setAddress(dto.getAddress());
        supplier.setPhone(dto.getPhone());
        supplier.setActive(dto.getActive());
        supplier.setDeleted(false);
        return supplier;
    }

    public Supplier toEntity(Long id) {
        if (id == null) return null;

        Supplier supplier = new Supplier();
        supplier.setId(id);
        return supplier;
    }

    public SupplierDTO toDTO(Supplier entity) {
        if (entity == null) return null;

        SupplierDTO dto = new SupplierDTO();
        dto.setId(entity.getId());
        dto.setRuc(entity.getRuc());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setActive(entity.getActive());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public void updateEntity(Supplier entity, SupplierUpdateDTO dto) {
        if (entity == null || dto == null) return;

        entity.setRuc(dto.getRuc());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setActive(dto.getActive());
    }
}
