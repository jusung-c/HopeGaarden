package io.jus.hopegaarden.controller.auth.request;

public record SignupRequest(
        String nickname,
        String email,
        String password
) {
}
