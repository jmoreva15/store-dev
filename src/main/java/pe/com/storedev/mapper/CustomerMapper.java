package pe.com.storedev.mapper;

import org.springframework.stereotype.Component;
import pe.com.storedev.dto.customer.CustomerCreateDTO;
import pe.com.storedev.dto.customer.CustomerDTO;
import pe.com.storedev.dto.customer.CustomerUpdateDTO;
import pe.com.storedev.entity.Customer;

@Component
public class CustomerMapper {
    public Customer toEntity(CustomerCreateDTO dto) {
        if (dto == null) return null;

        Customer customer = new Customer();
        customer.setDocumentType(dto.getDocumentType());
        customer.setDocumentNumber(dto.getDocumentNumber());
        customer.setName(dto.getName());
        customer.setAddress(dto.getAddress());
        customer.setPhone(dto.getPhone());
        customer.setDeleted(false);
        return customer;
    }

    public CustomerDTO toDTO(Customer entity) {
        if (entity == null) return null;

        CustomerDTO dto = new CustomerDTO();
        dto.setId(entity.getId());
        dto.setDocumentType(entity.getDocumentType());
        dto.setDocumentNumber(entity.getDocumentNumber());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public void updateEntity(Customer entity, CustomerUpdateDTO dto) {
        if (entity == null || dto == null) return;

        entity.setDocumentType(dto.getDocumentType());
        entity.setDocumentNumber(dto.getDocumentNumber());
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
    }
}
