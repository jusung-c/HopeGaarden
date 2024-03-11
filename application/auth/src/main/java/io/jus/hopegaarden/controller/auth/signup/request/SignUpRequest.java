package io.jus.hopegaarden.controller.auth.signup.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import validation.IsEmail;

@Builder
public record SignUpRequest(
        @NotBlank(message = "닉네임을 입력해주세요.")
        String nickname,

        @IsEmail
        @NotBlank(message = "이메일을 입력해주세요.")
        String email,

        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=]).*$",
                message = "비밀번호는 최소한 하나의 대문자, 소문자, 특수문자 및 숫자를 포함해야 합니다.")
        String password,

        @NotBlank(message = "패스워드를 확인해주세요.")
        String passwordVerify
) {
        // encode된 password를 설정하는 메서드 추가
        public SignUpRequest withEncodedPassword(String encodedPassword) {
                return new SignUpRequest(nickname(), email(), encodedPassword, passwordVerify());
        }
}
