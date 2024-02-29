package io.jus.hopegaarden.controller.auth;

import io.jus.hopegaarden.controller.auth.request.AuthRequest;
import io.jus.hopegaarden.controller.auth.response.LoginResponse;
import io.jus.hopegaarden.sevice.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody AuthRequest login) {

        return ResponseEntity.ok(authService.authenticate(login));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }
}
