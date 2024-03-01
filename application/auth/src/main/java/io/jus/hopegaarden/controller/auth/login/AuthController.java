package io.jus.hopegaarden.controller.auth.login;

import io.jus.hopegaarden.controller.auth.login.request.AuthRequest;
import io.jus.hopegaarden.controller.auth.login.response.LoginResponse;
import io.jus.hopegaarden.sevice.auth.login.AuthService;
import io.jus.hopegaarden.sevice.auth.login.response.AuthResponse;
import io.jus.hopegaarden.sevice.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid AuthRequest login) {

        AuthResponse authResponse = authService.authenticate(login);

        // refresh token을 쿠키로 관리하기 위해 쿠키 생성
        ResponseCookie refreshCookie = ResponseCookie
                .from("refreshToken", authResponse.refreshToken())
                .httpOnly(true)             // HTTP 전송 전용 쿠키
                .secure(true)               // HTTPS 연결에서만 전송
                .path("/")                  // 쿠키의 경로 설정 -> 모든 요청에서 쿠키를 사용할 수 있도록 "/"로 설정
                .maxAge(604800)   // 쿠키의 유효 시간을 7일로 설정
                .domain("localhost")        // 쿠키의 도메인 설정 -> 로컬 호스트에서만 전송되도록
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(LoginResponse.builder()
                        .accessToken(authResponse.accessToken())
                        .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@CookieValue(name = "refreshToken") String refreshToken) throws IOException {

        return ResponseEntity.ok()
                .body(LoginResponse.builder()
                        .accessToken(authService.refreshToken(refreshToken))
                        .build());
    }
}
