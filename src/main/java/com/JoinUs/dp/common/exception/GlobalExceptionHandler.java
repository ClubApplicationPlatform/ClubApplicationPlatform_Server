package com.JoinUs.dp.common.exception;

import com.JoinUs.dp.common.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /** 404: Not Found */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<Void>> handleNotFound(NotFoundException ex) {
        log.warn("[404 NOT FOUND] {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Response<>(404, null, ex.getMessage()));
    }

    /** 400: Bad Request */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response<Void>> handleBadRequest(BadRequestException ex) {
        log.warn("[400 BAD REQUEST] {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Response<>(400, null, ex.getMessage()));
    }

    /** 400: Validation Error */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Void>> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().isEmpty()
                ? "요청 값이 올바르지 않습니다."
                : ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.warn("[400 VALIDATION] {}", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Response<>(400, null, message));
    }

    /** 401 Unauthorized */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Response<Void>> handleUnauthorized(UnauthorizedException ex) {
        log.warn("[401 UNAUTHORIZED] {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new Response<>(401, null, ex.getMessage()));
    }

    /** 409 Conflict */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Response<Void>> handleConflict(ConflictException ex) {
        log.warn("[409 CONFLICT] {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new Response<>(409, null, ex.getMessage()));
    }

    /** 500: 기타 오류 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Void>> handleOther(Exception ex) {
        log.error("[500 SERVER ERROR] {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Response<>(500, null, "서버 오류가 발생했습니다."));
    }
}
