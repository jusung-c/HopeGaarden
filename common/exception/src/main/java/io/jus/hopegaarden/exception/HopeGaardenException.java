package io.jus.hopegaarden.exception;

public class HopeGaardenException extends RuntimeException {
    private ErrorCode errorCode;

    public HopeGaardenException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
