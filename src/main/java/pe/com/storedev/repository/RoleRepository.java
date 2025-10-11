package pe.com.storedev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.storedev.entity.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.deleted = FALSE")
    Page<Role> findAllNotDeleted(Pageable pageable);

    @Query("SELECT r FROM Role r WHERE r.deleted = FALSE AND r.active = TRUE")
    List<Role> findAllActiveNotDeleted();

    @Query("SELECT r FROM Role r WHERE r.id = :id AND r.deleted = FALSE")
    Optional<Role> findByIdNotDeleted(Long id);
}