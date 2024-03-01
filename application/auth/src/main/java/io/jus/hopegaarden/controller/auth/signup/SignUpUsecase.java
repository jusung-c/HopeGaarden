package io.jus.hopegaarden.controller.auth.signup;

import io.jus.hopegaarden.controller.auth.signup.request.SignUpRequest;

public interface SignUpUsecase {

    // 계정 생성 및 인증 메일 발송
    void requestSignup(SignUpRequest request);

    // 인증 메일 재전송
    void resendMail(String email);
}
