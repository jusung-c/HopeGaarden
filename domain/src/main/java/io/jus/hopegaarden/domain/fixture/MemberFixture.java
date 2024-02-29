package io.jus.hopegaarden.domain.fixture;

import io.jus.hopegaarden.domain.define.member.Member;
import io.jus.hopegaarden.domain.define.member.constant.MemberRole;

public class MemberFixture {

    public static Member 일반_유저_생성() {
        return Member.builder()
                .nickname("nickname")
                .password("password")
                .email("email")
                .role(MemberRole.MEMBER)
                .build();
    }
}
