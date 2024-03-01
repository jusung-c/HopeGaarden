package io.jus.hopegaarden.controller.auth.signup.request;

import jakarta.validation.constraints.NotBlank;
import validation.IsEmail;

public record SignUpRequest(
        @NotBlank(message = "닉네임을 입력해주세요.")
        String nickname,

        @IsEmail
        @NotBlank(message = "이메일을 입력해주세요.")
        String email,

        @NotBlank(message = "패스워드를 입력해주세요.")
        String password,

        @NotBlank(message = "패스워드를 확인해주세요.")
        String passwordVerify
) {
}
