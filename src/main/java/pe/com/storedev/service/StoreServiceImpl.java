package pe.com.storedev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.storedev.dto.store.StoreCreateDTO;
import pe.com.storedev.dto.store.StoreDTO;
import pe.com.storedev.dto.store.StoreUpdateDTO;
import pe.com.storedev.entity.Store;
import pe.com.storedev.exception.NotFoundException;
import pe.com.storedev.mapper.StoreMapper;
import pe.com.storedev.repository.StoreRepository;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<StoreDTO> findAll(Pageable pageable) {
        return storeRepository.findAllNotDeleted(pageable)
                .map(storeMapper::toDTO);
    }

    @Override
    public StoreDTO create(StoreCreateDTO dto) {
        Store store = storeMapper.toEntity(dto);
        Store saved = storeRepository.save(store);
        return storeMapper.toDTO(saved);
    }

    @Override
    public StoreDTO update(Long id, StoreUpdateDTO dto) {
        Store existing = storeRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Store not found with ID: " + id));

        storeMapper.updateEntity(existing, dto);
        Store updated = storeRepository.save(existing);
        return storeMapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public StoreDTO findById(Long id) {
        Store store = storeRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Store not found with ID: " + id));
        return storeMapper.toDTO(store);
    }

    @Override
    public void delete(Long id) {
        Store store = storeRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Store not found with ID: " + id));

        store.setDeleted(Boolean.TRUE);
        storeRepository.save(store);
    }
}
