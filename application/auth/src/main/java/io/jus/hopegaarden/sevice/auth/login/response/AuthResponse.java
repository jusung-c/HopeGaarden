package io.jus.hopegaarden.sevice.auth.login.response;

import lombok.Builder;

@Builder
public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}