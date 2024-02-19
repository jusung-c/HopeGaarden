package io.jus.hopegaarden.config.advice;

import io.jus.hopegaarden.domain.define.member.constant.MemberRole;
import io.jus.hopegaarden.exception.exceptions.RoleNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberExceptionHandlerTest {
    @Test
    void Role_form_메서드_예외_테스트() {
        // given
        String test = "test";

        // when, then
        assertThrows(RoleNotFoundException.class, () -> {
            MemberRole.from(test);
        });
    }

}