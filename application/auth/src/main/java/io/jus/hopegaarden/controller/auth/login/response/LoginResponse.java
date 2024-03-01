package io.jus.hopegaarden.controller.auth.login.response;

import lombok.Builder;

@Builder
public record LoginResponse(
        String accessToken
) {
}