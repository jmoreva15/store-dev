package pe.com.storedev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.storedev.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.deleted = FALSE")
    Page<Product> findAllNotDeleted(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.deleted = FALSE AND p.active = TRUE")
    List<Product> findAllActiveNotDeleted();

    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.deleted = FALSE")
    Optional<Product> findByIdNotDeleted(Long id);
}
