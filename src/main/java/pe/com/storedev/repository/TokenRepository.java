package pe.com.storedev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.storedev.entity.Token;
import pe.com.storedev.entity.TokenType;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValueAndTypeAndActiveTrue(String value, TokenType type);
}
