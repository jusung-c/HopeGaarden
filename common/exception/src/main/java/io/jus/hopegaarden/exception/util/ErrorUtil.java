package io.jus.hopegaarden.exception.util;

import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ErrorUtil {

    public static ResponseEntity<ErrorResponse> getErrorResponseResponseEntity(ErrorCode errorCode) {
        log.error("[HG ERROR]: {}", errorCode.getMessage());
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode));
    }
}
