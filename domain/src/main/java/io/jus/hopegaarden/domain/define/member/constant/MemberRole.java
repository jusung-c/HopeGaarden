package io.jus.hopegaarden.domain.define.member.constant;

import io.jus.hopegaarden.exception.exceptions.RoleNotFoundException;
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
                .orElseThrow(RoleNotFoundException::new);
    }
}
