package io.jus.hopegaarden.domain.define.token.jwt;

import io.jus.hopegaarden.domain.define.BaseEntity;
import io.jus.hopegaarden.domain.define.token.jwt.constant.TokenType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="TOKEN")
@Entity
public class JwtToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private boolean expired;

    private boolean revoked;

    private String email;

    @Builder
    public JwtToken(String token, TokenType tokenType, boolean expired, boolean revoked, String email) {
        this.token = token;
        this.tokenType = tokenType;
        this.expired = expired;
        this.revoked = revoked;
        this.email = email;
    }
}
