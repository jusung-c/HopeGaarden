package io.jus.hopegaarden.exception.exceptions.auth;

import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.exceptions.HopeGaardenException;

public class LoginInvalidException extends HopeGaardenException {

    public LoginInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
