package pe.com.storedev.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.storedev.dto.store.StoreCreateDTO;
import pe.com.storedev.dto.store.StoreDTO;
import pe.com.storedev.dto.store.StoreUpdateDTO;

public interface StoreService {
    Page<StoreDTO> findAll(Pageable pageable);
    StoreDTO create(StoreCreateDTO storeCreateDTO);
    StoreDTO update(Long id, StoreUpdateDTO storeUpdateDTO);
    StoreDTO findById(Long id);
    void delete(Long id);
}
