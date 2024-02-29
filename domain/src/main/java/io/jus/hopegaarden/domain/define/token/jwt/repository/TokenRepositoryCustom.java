package io.jus.hopegaarden.domain.define.token.jwt.repository;

import io.jus.hopegaarden.domain.define.token.jwt.JwtToken;

import java.util.List;

public interface TokenRepositoryCustom {

    // email에 해당하는 사용자의 유효한 Jwt 토큰 리스트 조회
    List<JwtToken> findAllValidTokenByUserId(String email);
}
