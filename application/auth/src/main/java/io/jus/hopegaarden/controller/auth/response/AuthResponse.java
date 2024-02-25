package io.jus.hopegaarden.controller.auth.response;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}