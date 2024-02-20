package io.jus.hopegaarden.exception.auth;


import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.HopeGaardenException;

public class MalformedTokenException extends HopeGaardenException {

    public MalformedTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}