package pe.com.storedev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.storedev.entity.Purchase;

import java.time.LocalDateTime;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    @Query("""
        SELECT p
        FROM Purchase p
        WHERE (:supplierId IS NULL OR p.supplier.id = :supplierId)
          AND (:storeId IS NULL OR p.store.id = :storeId)
          AND (:startDate IS NULL OR p.createdAt >= :startDate)
          AND (:endDate IS NULL OR p.createdAt <= :endDate)
        ORDER BY p.createdAt DESC
        """)
    Page<Purchase> findAllFilteredBySupplierStoreAndDate(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("supplierId") Long supplierId,
            @Param("storeId") Long storeId,
            Pageable pageable
    );
}
