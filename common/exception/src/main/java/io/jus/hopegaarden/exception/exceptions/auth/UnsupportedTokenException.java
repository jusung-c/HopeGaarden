package io.jus.hopegaarden.exception.exceptions.auth;

import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.exceptions.HopeGaardenException;

public class UnsupportedTokenException extends HopeGaardenException {

    public UnsupportedTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}