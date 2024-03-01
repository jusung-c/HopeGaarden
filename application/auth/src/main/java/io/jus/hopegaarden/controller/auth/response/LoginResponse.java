package io.jus.hopegaarden.controller.auth.response;

import lombok.Builder;

@Builder
public record LoginResponse(
        String accessToken
) {
}