package pe.com.storedev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.storedev.entity.StoreProduct;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProduct, Long> {
    @Query("SELECT s FROM StoreProduct s WHERE s.product.id = :productId AND s.store.id = :storeId")
    Optional<StoreProduct> findAssignment(Long productId, Long storeId);

    @Query("SELECT s FROM StoreProduct s WHERE s.store.id = :storeId")
    List<StoreProduct> findByStoreId(Long storeId);
}
