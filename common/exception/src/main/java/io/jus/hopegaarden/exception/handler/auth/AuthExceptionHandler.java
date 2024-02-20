package io.jus.hopegaarden.exception.handler.auth;

import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.ErrorResponse;
import io.jus.hopegaarden.exception.exceptions.auth.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static io.jus.hopegaarden.exception.util.ErrorUtil.getErrorResponseResponseEntity;

@Slf4j
@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(SignatureInvalidException.class)
    public ResponseEntity<ErrorResponse> handleSignatureInvalidException(final SignatureInvalidException e) {
        ErrorCode errorCode = e.getErrorCode();
        return getErrorResponseResponseEntity(errorCode);    }

    @ExceptionHandler(MalformedTokenException.class)
    public ResponseEntity<ErrorResponse> handleTokenFormInvalidException(final MalformedTokenException e) {
        ErrorCode errorCode = e.getErrorCode();
        return getErrorResponseResponseEntity(errorCode);    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ErrorResponse> handleExpiredTokenException(final ExpiredTokenException e) {
        ErrorCode errorCode = e.getErrorCode();
        return getErrorResponseResponseEntity(errorCode);    }

    @ExceptionHandler(UnsupportedTokenException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedTokenException(final UnsupportedTokenException e) {
        ErrorCode errorCode = e.getErrorCode();
        return getErrorResponseResponseEntity(errorCode);
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<ErrorResponse> handleTokenInvalidException(final TokenInvalidException e) {
        ErrorCode errorCode = e.getErrorCode();
        return getErrorResponseResponseEntity(errorCode);
    }

    @ExceptionHandler(LoginInvalidException.class)
    public ResponseEntity<ErrorResponse> handleLoginInvalidException(final LoginInvalidException e) {
        ErrorCode errorCode = e.getErrorCode();
        return getErrorResponseResponseEntity(errorCode);
    }
}

