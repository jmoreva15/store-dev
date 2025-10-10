package pe.com.storedev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.storedev.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}