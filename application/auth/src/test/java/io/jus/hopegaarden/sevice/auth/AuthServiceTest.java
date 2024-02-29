package io.jus.hopegaarden.sevice.auth;

import io.jus.hopegaarden.controller.auth.request.AuthRequest;
import io.jus.hopegaarden.domain.define.member.Member;
import io.jus.hopegaarden.domain.define.member.repository.MemberRepository;
import io.jus.hopegaarden.domain.define.token.jwt.JwtToken;
import io.jus.hopegaarden.domain.define.token.jwt.constant.TokenType;
import io.jus.hopegaarden.domain.define.token.jwt.repository.TokenRepository;
import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.exceptions.auth.TokenInvalidException;
import io.jus.hopegaarden.sevice.auth.response.AuthResponse;
import io.jus.hopegaarden.sevice.jwt.JwtTokenProvider;
import io.jus.hopegaarden.utils.IntegrationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static io.jus.hopegaarden.domain.fixture.MemberFixture.일반_유저_생성;
import static io.jus.hopegaarden.utils.IntegrationHelper.NON_ASCII;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SuppressWarnings(NON_ASCII)
class AuthServiceTest extends IntegrationHelper {

    @MockBean
    AuthenticationManager authenticationManager;

    @Autowired
    AuthService authService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    private Member member;

    @BeforeEach
    void setup() {
        member = 일반_유저_생성();
        memberRepository.save(member);
    }

    @Test
    void 로그인_테스트() {
        // given
        AuthRequest login = AuthRequest.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .build();

        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenReturn(any(Authentication.class));

        // when
        AuthResponse response = authService.authenticate(login);

        // then
        assertNotNull(response);
        assertNotNull(response.accessToken());
        assertNotNull(response.refreshToken());

    }

    @Test
    void 토큰_재발급_성공_테스트() throws IOException {
        // given
        String refreshToken = jwtTokenProvider.generateRefreshToken(member);
        tokenRepository.save(JwtToken.builder()
                .email(member.getEmail())
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .token(refreshToken)
                .build());

        // when
        String newToken = authService.refreshToken(refreshToken);

        // then
        assertNotNull(newToken);
        assertThat(jwtTokenProvider.extractSubject(newToken)).isEqualTo(member.getEmail());

    }

    @Test
    void 유효하지_않은_토큰으로_재발급_시도시_실패_테스트() throws IOException {
        // given
        String refreshToken = jwtTokenProvider.generateRefreshToken(member);
        tokenRepository.save(JwtToken.builder()
                .email(member.getEmail())
                .tokenType(TokenType.BEARER)
                .expired(true)
                .revoked(true)
                .token(refreshToken)
                .build());

        // when
        TokenInvalidException exception = assertThrows(TokenInvalidException.class, () -> {
            authService.refreshToken(refreshToken);
        });
        assertEquals(exception.getMessage(), ErrorCode.JWT_REFRESH_TOKEN_INVALID.getMessage());

    }

    @Test
    void null인_토큰으로_재발급_요청시_실패_테스트() {

        TokenInvalidException exception = assertThrows(TokenInvalidException.class, () -> {
            authService.refreshToken(null);
        });
        assertEquals(exception.getMessage(), ErrorCode.JWT_REFRESH_TOKEN_IS_NULL.getMessage());

    }
}