package pe.com.storedev.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.storedev.dto.product.AssignProductDTO;
import pe.com.storedev.dto.store.StoreCreateDTO;
import pe.com.storedev.dto.store.StoreDTO;
import pe.com.storedev.dto.store.StoreProductDTO;
import pe.com.storedev.dto.store.StoreUpdateDTO;

import java.util.List;

public interface StoreService {
    Page<StoreDTO> findAll(Pageable pageable);
    List<StoreDTO> findAllActive();
    List<StoreProductDTO> findAllProductAssignments();
    StoreDTO create(StoreCreateDTO storeCreateDTO);
    StoreDTO update(Long id, StoreUpdateDTO storeUpdateDTO);
    StoreDTO findById(Long id);
    StoreProductDTO assignProduct(AssignProductDTO dto);
    void delete(Long id);
}
