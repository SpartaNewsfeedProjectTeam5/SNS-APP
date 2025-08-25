package org.example.snsapp.global.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 발생 예외 처리
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 커스텀 예외 처리
     *
     * @param ex      커스텀 예외
     * @param request HTTP 인증 정보
     * @return 유저 에러 응답 DTO
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustom(CustomException ex,
                                                      HttpServletRequest request) {
        ErrorResponse error = ErrorResponse.of(ex.getStatus(), ex.getCode(), ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, ex.getStatus());
    }

    /**
     * Valid 예외 처리
     *
     * @param ex      Valid 예외
     * @param request HTTP 인증 정보
     * @return 유저 에러 응답 DTO
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                          HttpServletRequest request) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponse error = ErrorResponse.of(HttpStatus.BAD_REQUEST, "VAL-000", message, request.getRequestURI());
        return ResponseEntity.badRequest().body(error);
    }
}
