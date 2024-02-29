package io.jus.hopegaarden.exception.exceptions.auth;


import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.exceptions.HopeGaardenException;

public class TokenInvalidException extends HopeGaardenException {

    public TokenInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}