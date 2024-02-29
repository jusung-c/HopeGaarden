package io.jus.hopegaarden.domain.define.token.refresh.repository;

import io.jus.hopegaarden.domain.define.token.refresh.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
