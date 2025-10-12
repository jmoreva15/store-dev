package pe.com.storedev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.storedev.entity.Store;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT s FROM Store s WHERE s.deleted = FALSE")
    Page<Store> findAllNotDeleted(Pageable pageable);

    @Query("SELECT s FROM Store s WHERE s.id = :id AND s.deleted = FALSE")
    Optional<Store> findByIdNotDeleted(Long id);
}
