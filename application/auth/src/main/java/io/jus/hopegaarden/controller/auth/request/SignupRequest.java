package io.jus.hopegaarden.controller.auth.request;

import jakarta.validation.constraints.NotBlank;
import validation.IsEmail;

public record SignupRequest(
        @NotBlank(message = "닉네임을 입력해주세요.")
        String nickname,

        @IsEmail
        @NotBlank(message = "이메일을 입력해주세요.")
        String email,

        @NotBlank(message = "패스워드를 입력해주세요.")
        String password
) {
}
