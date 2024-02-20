package io.jus.hopegaarden.exception.exceptions.member;

import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.exceptions.HopeGaardenException;

public class RoleNotFoundException extends HopeGaardenException {

    public RoleNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
