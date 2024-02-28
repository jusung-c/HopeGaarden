package io.jus.hopegaarden.sevice;

import io.jus.hopegaarden.domain.define.member.Member;
import io.jus.hopegaarden.domain.define.token.jwt.JwtToken;
import io.jus.hopegaarden.domain.define.token.jwt.repository.TokenRepository;
import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.exceptions.auth.TokenInvalidException;
import io.jus.hopegaarden.sevice.jwt.JwtTokenProvider;
import io.jus.hopegaarden.utils.AuthenticationExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {
        String jwtToken = AuthenticationExtractor.extractToken(request)
                .orElseThrow(() -> {
                    log.error("[HG ERROR]: {}", ErrorCode.JWT_NOT_FOUND.getMessage());
                    return new TokenInvalidException(ErrorCode.JWT_NOT_FOUND);
                });
        String subject = jwtTokenProvider.extractSubject(jwtToken);
        revokeAllUserTokens(subject);

        log.info("[HG INFO]: {} 님이 로그아웃하셨습니다.", subject);

    }

    private void revokeAllUserTokens(String userEmail) {
        List<JwtToken> validTokens = tokenRepository.findAllValidTokenByUserId(userEmail);
        if (!validTokens.isEmpty()) {
            validTokens.forEach(JwtToken::setTokenInvalid);
            tokenRepository.saveAll(validTokens);
        }
    }

}
