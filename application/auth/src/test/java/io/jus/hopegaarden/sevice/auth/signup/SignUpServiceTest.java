package io.jus.hopegaarden.sevice.auth.signup;

import io.jus.hopegaarden.controller.auth.signup.request.SignUpRequest;
import io.jus.hopegaarden.domain.define.member.Member;
import io.jus.hopegaarden.domain.define.member.repository.MemberRepository;
import io.jus.hopegaarden.domain.fixture.MemberFixture;
import io.jus.hopegaarden.utils.IntegrationHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static io.jus.hopegaarden.utils.IntegrationHelper.NON_ASCII;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings(NON_ASCII)
class SignUpServiceTest extends IntegrationHelper {

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원가입_성공_테스트() {
        // given
        Member member = MemberFixture.일반_유저_생성();

        var request = SignUpRequest.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .password(member.getPassword())
                .passwordVerify(member.getPassword())
                .build();

        // when
        signUpService.requestSignup(request);
        Member findMember = memberRepository.findByEmail(member.getEmail()).get();

        // then
        assertSoftly(softly -> {
            softly.assertThat(findMember).isNotNull();
            softly.assertThat(findMember.getEmail()).isEqualTo(request.email());
            softly.assertThat(findMember.getPassword()).isEqualTo(request.password());
        });
    }

}