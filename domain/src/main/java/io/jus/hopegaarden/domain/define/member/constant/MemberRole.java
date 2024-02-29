package io.jus.hopegaarden.domain.define.member.constant;

import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.exceptions.member.RoleNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum MemberRole {

    MEMBER("member"),
    ADMIN("admin");

    private final String role;

    public static MemberRole from(final String role) {
        return Arrays.stream(values())
                .filter(value -> value.role.equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new RoleNotFoundException(ErrorCode.MEMBER_ROLE_NOT_FOUND));
    }

    // ADMIN 체크
    public boolean isAdministrator() {
        return this.equals(ADMIN);
    }
}
