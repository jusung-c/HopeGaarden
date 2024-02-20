package io.jus.hopegaarden.exception.auth;


import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.HopeGaardenException;

public class TokenInvalidException extends HopeGaardenException {

    public TokenInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}