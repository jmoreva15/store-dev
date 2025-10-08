package pe.com.storedev.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.storedev.dto.customer.CustomerCreateDTO;
import pe.com.storedev.dto.customer.CustomerDTO;
import pe.com.storedev.dto.customer.CustomerUpdateDTO;

public interface CustomerService {
    Page<CustomerDTO> findAll(Pageable pageable);
    CustomerDTO create(CustomerCreateDTO customerCreateDTO);
    CustomerDTO update(Long id, CustomerUpdateDTO customerCreateDTO);
    CustomerDTO findById(Long id);
    void delete(Long id);
}
