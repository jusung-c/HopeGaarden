package io.jus.hopegaarden.controller.auth.signup;

import io.jus.hopegaarden.controller.auth.signup.request.SignUpRequest;

public interface SignUpUsecase {

    // 사용자 정보 입력 후 회원가입 요청시 유효성 검사
    void checkRequiredItem(SignUpRequest request);

    // 회원정보 유효성 확인 완료시 계정 생성 및 인증 메일 발송
    void requestSignup(SignUpRequest request);

    // 인증 메일 재전송
    void resendMail(String email);
}
