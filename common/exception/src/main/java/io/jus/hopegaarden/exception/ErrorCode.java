package io.jus.hopegaarden.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
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
    JWT_TOKEN_INVALID(401, HttpStatus.UNAUTHORIZED, "더 이상 사용할 수 없는 JWT 토큰입니다."),
    JWT_ILLEGAL_ARGUMENT(400, HttpStatus.BAD_REQUEST, "JWT 토큰의 구성 요소가 올바르지 않습니다."),
    JWT_INVALID_HEADER(400, HttpStatus.BAD_REQUEST, "Header의 형식이 올바르지 않습니다."),
    JWT_SUBJECT_IS_NULL(400, HttpStatus.BAD_REQUEST, "해당 JWT 토큰의 식별자가 null입니다."),
    JWT_NOT_FOUND(400, HttpStatus.BAD_REQUEST, "요청의 헤더에서 JWT 토큰을 읽어올 수 없습니다."),

    // Jwt Refresh Token
    JWT_REFRESH_TOKEN_IS_NULL(400, HttpStatus.BAD_REQUEST, "쿠키에서 얻어온 refresh token이 null입니다."),
    JWT_REFRESH_TOKEN_INVALID(401, HttpStatus.UNAUTHORIZED, "더 이상 사용할 수 없는 refresh token입니다."),

    // Member
    MEMBER_NOT_FOUND(404, HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    MEMBER_ROLE_NOT_FOUND(404, HttpStatus.NOT_FOUND, "사용자의 권한을 찾을 수 없습니다."),

    ;

    private final int value;
    private final HttpStatus status;
    private final String message;




}
