package io.jus.hopegaarden.exception.auth;

import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.HopeGaardenException;

public class LoginInvalidException extends HopeGaardenException {

    public LoginInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
