package io.jus.hopegaarden.sevice.auth.login;

import io.jus.hopegaarden.controller.auth.login.request.AuthRequest;
import io.jus.hopegaarden.domain.define.member.Member;
import io.jus.hopegaarden.domain.define.member.repository.MemberRepository;
import io.jus.hopegaarden.domain.define.token.jwt.JwtToken;
import io.jus.hopegaarden.domain.define.token.jwt.constant.TokenType;
import io.jus.hopegaarden.domain.define.token.jwt.repository.TokenRepository;
import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.exceptions.auth.TokenInvalidException;
import io.jus.hopegaarden.exception.exceptions.member.MemberNotFoundException;
import io.jus.hopegaarden.sevice.auth.login.response.AuthResponse;
import io.jus.hopegaarden.sevice.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private static final String ROLE_CLAIM = "role";
    private static final String NICKNAME_CLAIM = "nickname";
    private static final String PASSWORD_CLAIM = "password";

    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;

    @Transactional
    public AuthResponse authenticate(AuthRequest login) {
        Member member = memberRepository.findByEmail(login.email()).orElseThrow(() -> {
            log.error("[HG ERROR]: {}", ErrorCode.MEMBER_NOT_FOUND.getMessage());
            throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
        });

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                login.email(),
                login.password()
        ));

        log.info("[HG INFO]: {} 남이 로그인에 성공하셨습니다.", member.getNickname());

        // 이전의 모든 JWT 토큰 사용 불가능하도록 업데이트
        revokeAllUserTokens(member);

        // JWT 토큰 생성
        String jwtToken = jwtTokenProvider.generateToken(setClaims(member), member);
        saveJwtToken(member, jwtToken);

        // refresh token 생성
        String refreshToken = jwtTokenProvider.generateRefreshToken(member);
        saveJwtToken(member, refreshToken);

        return new AuthResponse(jwtToken, refreshToken);
    }

    @Transactional
    public String refreshToken(String refreshToken) throws IOException {
        if (refreshToken == null) {
            log.error("[HG ERROR]: {}", ErrorCode.JWT_REFRESH_TOKEN_IS_NULL.getMessage());
            throw new TokenInvalidException(ErrorCode.JWT_REFRESH_TOKEN_IS_NULL);
        }

        final String userEmail = jwtTokenProvider.extractSubject(refreshToken);

        if (userEmail == null) {
            log.error("[HG ERROR]: {}", ErrorCode.JWT_SUBJECT_IS_NULL.getMessage());
            throw new TokenInvalidException(ErrorCode.JWT_SUBJECT_IS_NULL);
        }

        Member member = memberRepository.findByEmail(userEmail).orElseThrow(() -> {
            log.error("[HG ERROR]: {}", ErrorCode.MEMBER_NOT_FOUND.getMessage());
            throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
        });

        // 활성화된 refreshToken인지 확인
        if (jwtTokenProvider.isRefreshTokenValid(refreshToken, member) && isTokenValid(refreshToken)) {
            // Jwt 재발급
            String newJwtToken = jwtTokenProvider.generateToken(setClaims(member), member);
            saveJwtToken(member, newJwtToken);

            return newJwtToken;
        } else {
            log.error("[HG ERROR]: {}", ErrorCode.JWT_REFRESH_TOKEN_INVALID.getMessage());
            throw new TokenInvalidException(ErrorCode.JWT_REFRESH_TOKEN_INVALID);
        }

    }

    private void revokeAllUserTokens(Member member) {
        List<JwtToken> validTokens = tokenRepository.findAllValidTokenByUserId(member.getEmail());
        if (!validTokens.isEmpty()) {
            validTokens.forEach(JwtToken::setTokenInvalid);
            tokenRepository.saveAll(validTokens);
        }
    }

    private static HashMap<String, String> setClaims(Member member) {
        HashMap<String, String> claims = new HashMap<>();
        claims.put(ROLE_CLAIM, member.getRole().name());
        claims.put(NICKNAME_CLAIM, member.getNickname());
        claims.put(PASSWORD_CLAIM, member.getPassword());
        return claims;
    }

    private void saveJwtToken(Member member, String jwtToken) {
        JwtToken token = JwtToken.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .email(member.getEmail())
                .build();

        tokenRepository.save(token);
    }

    private boolean isTokenValid(String refreshToken) {
        return tokenRepository.findByToken(refreshToken)
                .map(token -> !token.isExpired() && !token.isRevoked())
                .orElse(false);
    }

}
