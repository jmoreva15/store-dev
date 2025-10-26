package pe.com.storedev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.com.storedev.entity.StoreProduct;
import pe.com.storedev.repository.StoreProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreProductServiceImpl implements StoreProductService {
    private final StoreProductRepository storeProductRepository;

    @Override
    public List<StoreProduct> findByStoreId(Long storeId) {
        if(storeId == null) return List.of();
        return storeProductRepository.findByStoreId(storeId);
    }
}
