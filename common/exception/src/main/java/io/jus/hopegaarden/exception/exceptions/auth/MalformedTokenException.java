package io.jus.hopegaarden.exception.exceptions.auth;


import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.exceptions.HopeGaardenException;

public class MalformedTokenException extends HopeGaardenException {

    public MalformedTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}