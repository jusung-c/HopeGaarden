package io.jus.hopegaarden.exception.exceptions;

import io.jus.hopegaarden.exception.ErrorCode;
import lombok.Getter;

@Getter
public class HopeGaardenException extends RuntimeException {
    private ErrorCode errorCode;

    public HopeGaardenException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
