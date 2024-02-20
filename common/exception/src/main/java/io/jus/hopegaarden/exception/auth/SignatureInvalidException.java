package io.jus.hopegaarden.exception.auth;

import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.HopeGaardenException;

public class SignatureInvalidException extends HopeGaardenException {

    public SignatureInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}