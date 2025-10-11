package pe.com.storedev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.storedev.dto.supplier.SupplierCreateDTO;
import pe.com.storedev.dto.supplier.SupplierDTO;
import pe.com.storedev.dto.supplier.SupplierUpdateDTO;
import pe.com.storedev.entity.Supplier;
import pe.com.storedev.exception.NotFoundException;
import pe.com.storedev.mapper.SupplierMapper;
import pe.com.storedev.repository.SupplierRepository;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<SupplierDTO> findAll(Pageable pageable) {
        return supplierRepository.findAllNotDeleted(pageable)
                .map(supplierMapper::toDTO);
    }

    @Override
    public SupplierDTO create(SupplierCreateDTO supplierCreateDTO) {
        Supplier supplier = supplierMapper.toEntity(supplierCreateDTO);
        Supplier saved = supplierRepository.save(supplier);
        return supplierMapper.toDTO(saved);
    }

    @Override
    public SupplierDTO update(Long id, SupplierUpdateDTO supplierUpdateDTO) {
        Supplier existing = supplierRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Supplier not found with ID: " + id));

        supplierMapper.updateEntity(existing, supplierUpdateDTO);
        Supplier updated = supplierRepository.save(existing);
        return supplierMapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public SupplierDTO findById(Long id) {
        Supplier supplier = supplierRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Supplier not found with ID: " + id));
        return supplierMapper.toDTO(supplier);
    }

    @Override
    public void delete(Long id) {
        Supplier supplier = supplierRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Supplier not found with ID: " + id));

        supplier.setDeleted(Boolean.TRUE);
        supplierRepository.save(supplier);
    }
}
