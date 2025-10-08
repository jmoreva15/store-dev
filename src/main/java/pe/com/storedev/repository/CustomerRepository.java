package pe.com.storedev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.storedev.entity.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE c.deleted = FALSE")
    Page<Customer> findAllNotDeleted(Pageable pageable);

    @Query("SELECT c FROM Customer c WHERE c.id = :id AND c.deleted = FALSE")
    Optional<Customer> findByIdNotDeleted(Long id);
}
