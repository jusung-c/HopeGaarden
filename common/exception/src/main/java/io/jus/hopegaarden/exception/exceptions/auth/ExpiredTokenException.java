package io.jus.hopegaarden.exception.exceptions.auth;

import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.exceptions.HopeGaardenException;

public class ExpiredTokenException extends HopeGaardenException {

    public ExpiredTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}