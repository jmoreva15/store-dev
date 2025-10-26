package pe.com.storedev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.storedev.dto.product.AssignProductDTO;
import pe.com.storedev.dto.store.StoreCreateDTO;
import pe.com.storedev.dto.store.StoreDTO;
import pe.com.storedev.dto.store.StoreProductDTO;
import pe.com.storedev.dto.store.StoreUpdateDTO;
import pe.com.storedev.entity.Store;
import pe.com.storedev.exception.NotFoundException;
import pe.com.storedev.mapper.StoreMapper;
import pe.com.storedev.mapper.StoreProductMapper;
import pe.com.storedev.repository.StoreProductRepository;
import pe.com.storedev.repository.StoreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreMapper storeMapper;
    private final StoreProductMapper storeProductMapper;
    private final StoreRepository storeRepository;
    private final StoreProductRepository storeProductRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<StoreDTO> findAll(Pageable pageable) {
        return storeRepository.findAllNotDeleted(pageable)
                .map(storeMapper::toDTO);
    }

    @Override
    public List<StoreDTO> findAllActive() {
        return storeRepository.findAllActiveNotDeleted()
                .stream().map(storeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StoreProductDTO> findAllProductAssignments() {
        return storeProductRepository.findAll().stream()
                .map(storeProductMapper::toDTO)
                .collect(Collectors.toList());
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
    public StoreProductDTO assignProduct(AssignProductDTO dto) {
        boolean assignmentExists =  storeProductRepository
                .findAssignment(dto.getProductId(), dto.getStoreId())
                .isPresent();

        if (assignmentExists)
            throw new IllegalStateException("Product is already assigned to the store.");

        return storeProductMapper.toDTO(storeProductRepository
                        .save(storeProductMapper.toEntity(dto)));
    }

    @Override
    public void delete(Long id) {
        Store store = storeRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Store not found with ID: " + id));

        store.setDeleted(Boolean.TRUE);
        storeRepository.save(store);
    }
}
