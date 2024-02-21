package io.jus.hopegaarden.sevice;

import io.jus.hopegaarden.domain.define.token.jwt.JwtToken;
import io.jus.hopegaarden.domain.define.token.jwt.constant.TokenType;
import io.jus.hopegaarden.domain.define.token.jwt.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        jwt = authHeader.substring(7);

        tokenRepository.findByToken(jwt)
                .ifPresent(storedToken -> tokenRepository.save(JwtToken.builder()
                        .token(storedToken.getToken())
                        .email(storedToken.getEmail())
                        .expired(true)
                        .revoked(true)
                        .tokenType(TokenType.BEARER)
                        .build())
                );
    }
}
