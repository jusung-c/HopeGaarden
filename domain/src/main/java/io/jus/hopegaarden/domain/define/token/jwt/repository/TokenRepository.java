package io.jus.hopegaarden.domain.define.token.jwt.repository;

import io.jus.hopegaarden.domain.define.token.jwt.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<JwtToken, Long>, TokenRepositoryCustom {
    Optional<JwtToken> findByToken(String token);

}

