package pe.com.storedev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.storedev.entity.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE c.deleted = FALSE")
    Page<Category> findAllNotDeleted(Pageable pageable);

    @Query("SELECT c FROM Category c WHERE c.id = :id AND c.deleted = FALSE")
    Optional<Category> findByIdNotDeleted(Long id);
}
