package io.jus.hopegaarden.controller.auth.signup;

import io.jus.hopegaarden.controller.auth.signup.request.SignUpRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpUsecase signUpService;

    @PostMapping("/signup")
    public ResponseEntity<String> requestSignup(@RequestBody @Valid SignUpRequest request) {
        signUpService.requestSignup(request);

        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

}