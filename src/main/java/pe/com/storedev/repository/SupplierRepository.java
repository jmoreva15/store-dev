package pe.com.storedev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.storedev.entity.Supplier;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    @Query("SELECT s FROM Supplier s WHERE s.deleted = FALSE")
    Page<Supplier> findAllNotDeleted(Pageable pageable);

    @Query("SELECT s FROM Supplier s WHERE s.id = :id AND s.deleted = FALSE")
    Optional<Supplier> findByIdNotDeleted(Long id);
}
