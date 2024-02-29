package io.jus.hopegaarden.exception.handler.member;

import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.ErrorResponse;
import io.jus.hopegaarden.exception.exceptions.member.RoleNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static io.jus.hopegaarden.exception.util.ErrorUtil.getErrorResponseResponseEntity;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(final RoleNotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();
        return getErrorResponseResponseEntity(errorCode);
    }
}

