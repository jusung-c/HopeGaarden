package io.jus.hopegaarden.exception.exceptions.auth;

import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.exceptions.HopeGaardenException;

public class SignUpException extends HopeGaardenException {

    public SignUpException(ErrorCode errorCode) {
        super(errorCode);
    }
}