package io.jus.hopegaarden.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private int value;
    private HttpStatus status;
    private String message;

    public static ErrorResponse of(int value, HttpStatus status, String message) {
        return ErrorResponse.builder()
                .value(value)
                .status(status)
                .message(message)
                .build();
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .value(errorCode.getValue())
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .build();
    }
}
