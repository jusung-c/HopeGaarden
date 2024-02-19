package io.jus.hopegaarden.domain.define.member.fixture;

import io.jus.hopegaarden.domain.define.member.Member;
import io.jus.hopegaarden.domain.define.member.constant.MemberRole;

public class MemberFixture {

    public static Member 일반_유저_생성() {
        return Member.builder()
                .nickname("nickname")
                .email("email@email.com")
                .memberRole(MemberRole.MEMBER)
                .build();
    }
}
