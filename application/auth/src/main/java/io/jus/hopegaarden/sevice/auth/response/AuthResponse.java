package io.jus.hopegaarden.sevice.auth.response;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}