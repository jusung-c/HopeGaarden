package io.jus.hopegaarden.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public enum ErrorCode {

    // Global
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),


    // Auth


    // Jwt
    JWT_TOKEN_EXPIRED(401, HttpStatus.UNAUTHORIZED, "JWT 토큰이 만료되었습니다."),
    JWT_UNSUPPORTED(401, HttpStatus.UNAUTHORIZED, "지원하지 않는 JWT 토큰입니다."),
    JWT_MALFORMED(401, HttpStatus.UNAUTHORIZED, "올바른 JWT 토큰의 형태가 아닙니다."),
    JWT_SIGNATURE(401, HttpStatus.UNAUTHORIZED, "올바른 SIGNATURE가 아닙니다."),
    JWT_ILLEGAL_ARGUMENT(400, HttpStatus.BAD_REQUEST, "JWT 토큰의 구성 요소가 올바르지 않습니다."),

    // Member
    MEMBER_ROLE_NOT_FOUND(404, HttpStatus.NOT_FOUND, "멤버의 권한을 찾을 수 없습니다."),

    ;

    private final int value;
    private final HttpStatus status;
    private final String message;




}
