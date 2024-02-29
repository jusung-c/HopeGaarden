package io.jus.hopegaarden.exception.exceptions.member;

import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.exceptions.HopeGaardenException;

public class MemberNotFoundException extends HopeGaardenException {

    public MemberNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
