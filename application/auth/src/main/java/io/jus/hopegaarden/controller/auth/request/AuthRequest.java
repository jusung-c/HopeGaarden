package io.jus.hopegaarden.controller.auth.request;

import lombok.Builder;

@Builder
public record AuthRequest(
        String email,
        String password
) {
}
