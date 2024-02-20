package io.jus.hopegaarden.exception.exceptions.auth;

import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.exceptions.HopeGaardenException;

public class SignatureInvalidException extends HopeGaardenException {

    public SignatureInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}