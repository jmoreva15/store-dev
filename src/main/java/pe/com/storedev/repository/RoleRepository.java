package pe.com.storedev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.storedev.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}