package pe.com.storedev.service;

import pe.com.storedev.entity.StoreProduct;

import java.util.List;

public interface StoreProductService {
    List<StoreProduct> findByStoreId(Long storeId);
}
