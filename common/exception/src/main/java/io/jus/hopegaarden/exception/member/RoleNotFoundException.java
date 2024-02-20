package io.jus.hopegaarden.exception.member;

import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.HopeGaardenException;

public class RoleNotFoundException extends HopeGaardenException {

    public RoleNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
