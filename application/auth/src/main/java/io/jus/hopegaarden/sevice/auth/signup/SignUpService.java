package io.jus.hopegaarden.sevice.auth.signup;

import io.jus.hopegaarden.controller.auth.signup.SignUpUsecase;
import io.jus.hopegaarden.controller.auth.signup.request.SignUpRequest;
import io.jus.hopegaarden.domain.define.member.Member;
import io.jus.hopegaarden.domain.define.member.constant.MemberRole;
import io.jus.hopegaarden.domain.define.member.repository.MemberRepository;
import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.exceptions.auth.SignUpException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignUpService implements SignUpUsecase {

    private final MemberRepository memberRepository;

    @Override
    public void requestSignup(SignUpRequest request) {
        // 입력된 패스워드가 서로 일치하는지 확인
        verifyCommand(request);

        // 회원 등록
        Member savedMember = memberRepository.save(memberMapper.apply(request));

        // TODO : 인증메일 발송

    }

    @Override
    public void resendMail(String email) {
        // TODO : 인증메일 재발송

    }

    private void verifyCommand(SignUpRequest request) {
        if (!request.password().equals(request.passwordVerify())) {
            log.error("[HG ERROR]: {}", ErrorCode.PASSWORD_DO_NOT_MATCH.getMessage());
            throw new SignUpException(ErrorCode.PASSWORD_DO_NOT_MATCH);
        }
    }

    Function<SignUpRequest, Member> memberMapper = request -> Member.builder()
            .email(request.email())
            .nickname(request.nickname())
            .password(request.password())
            .role(MemberRole.MEMBER)
            .build();
}
