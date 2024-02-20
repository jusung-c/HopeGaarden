package io.jus.hopegaarden.exception.handler;

import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.ErrorResponse;
import io.jus.hopegaarden.exception.exceptions.HopeGaardenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import static io.jus.hopegaarden.exception.util.ErrorUtil.getErrorResponseResponseEntity;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /*
        데이터 바인딩 중 발생하는 에러 BindException 처리
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> bindException(BindException e) {
        String errorMessage = getErrorMessage(e.getBindingResult());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ErrorResponse.of(400, HttpStatus.BAD_REQUEST, errorMessage));
    }

    /*
        @Valid 어노테이션을 사용한 DTO의 유효성 검사에서 예외가 발생한 경우
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String errorMessage = getErrorMessage(e.getBindingResult());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(400, HttpStatus.BAD_REQUEST, errorMessage));
    }

    @ExceptionHandler(HopeGaardenException.class)
    protected ResponseEntity<ErrorResponse> handleHopeGaardenException(final HopeGaardenException e) {
        ErrorCode errorCode = e.getErrorCode();
        return getErrorResponseResponseEntity(errorCode);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e) {
        log.error("[HG ERROR]: {}", e.getMessage());
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(500, INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    private static String getErrorMessage(BindingResult e) {
        return e
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));
    }

}
