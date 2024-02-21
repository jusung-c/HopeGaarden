package io.jus.hopegaarden.domain.define.token.jwt.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.jus.hopegaarden.domain.define.token.jwt.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.jus.hopegaarden.domain.define.token.QJwtToken.jwtToken;

@Component
@RequiredArgsConstructor
public class TokenRepositoryCustomImpl implements TokenRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<JwtToken> findAllValidTokenByUserId(String email) {

        return jpaQueryFactory.selectFrom(jwtToken)
                .where(jwtToken.email.eq(email))
                .fetch();
    }
}
