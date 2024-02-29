package io.jus.hopegaarden.config.advice;

import io.jus.hopegaarden.domain.define.member.constant.MemberRole;
import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.exceptions.member.RoleNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberExceptionHandlerTest {

    @Test
    void MemberRole_from_테스트() {
        // given
        String validRole = "ADMIN";

        // when
        MemberRole memberRole = MemberRole.from(validRole);

        // then
        assertEquals(MemberRole.ADMIN, memberRole);
    }

    @Test
    void MemberRole_from_예외_테스트() {
        // given
        String invalidRole = "INVALID_ROLE";

        // when
        RoleNotFoundException exception = assertThrows(RoleNotFoundException.class, () -> MemberRole.from(invalidRole));

        // then
        assertEquals(ErrorCode.MEMBER_ROLE_NOT_FOUND, exception.getErrorCode());
    }

}