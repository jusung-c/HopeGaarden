package io.jus.hopegaarden.exception.auth;

import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.HopeGaardenException;

public class UnsupportedTokenException extends HopeGaardenException {

    public UnsupportedTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}