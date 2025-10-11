package pe.com.storedev.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.storedev.dto.supplier.SupplierCreateDTO;
import pe.com.storedev.dto.supplier.SupplierDTO;
import pe.com.storedev.dto.supplier.SupplierUpdateDTO;

public interface SupplierService {
    Page<SupplierDTO> findAll(Pageable pageable);
    SupplierDTO create(SupplierCreateDTO supplierCreateDTO);
    SupplierDTO update(Long id, SupplierUpdateDTO supplierUpdateDTO);
    SupplierDTO findById(Long id);
    void delete(Long id);
}
