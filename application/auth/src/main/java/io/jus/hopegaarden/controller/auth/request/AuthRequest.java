package io.jus.hopegaarden.controller.auth.request;

public record AuthRequest(
        String email,
        String password
) {
}
