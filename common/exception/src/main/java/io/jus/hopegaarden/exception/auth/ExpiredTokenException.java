package io.jus.hopegaarden.exception.auth;

import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.HopeGaardenException;

public class ExpiredTokenException extends HopeGaardenException {

    public ExpiredTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}