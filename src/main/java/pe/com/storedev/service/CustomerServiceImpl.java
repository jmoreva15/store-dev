package pe.com.storedev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.storedev.dto.customer.CustomerCreateDTO;
import pe.com.storedev.dto.customer.CustomerDTO;
import pe.com.storedev.dto.customer.CustomerUpdateDTO;
import pe.com.storedev.entity.Customer;
import pe.com.storedev.exception.NotFoundException;
import pe.com.storedev.mapper.CustomerMapper;
import pe.com.storedev.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerDTO> findAll(Pageable pageable) {
        return customerRepository.findAllNotDeleted(pageable)
                .map(customerMapper::toDTO);
    }

    @Override
    public CustomerDTO create(CustomerCreateDTO customerCreateDTO) {
        Customer customer = customerMapper.toEntity(customerCreateDTO);
        Customer saved = customerRepository.save(customer);
        return customerMapper.toDTO(saved);
    }

    @Override
    public CustomerDTO update(Long id, CustomerUpdateDTO customerUpdateDTO) {
        Customer existing = customerRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with ID: " + id));

        customerMapper.updateEntity(existing, customerUpdateDTO);
        Customer updated = customerRepository.save(existing);
        return customerMapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO findById(Long id) {
        Customer customer = customerRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with ID: " + id));
        return customerMapper.toDTO(customer);
    }

    @Override
    public void delete(Long id) {
        Customer customer = customerRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with ID: " + id));

        customer.setDeleted(true);
        customerRepository.save(customer);
    }
}
