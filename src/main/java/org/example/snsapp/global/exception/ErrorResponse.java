package org.example.snsapp.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * 에러 응답 DTO
 */
@Getter
public class ErrorResponse {
    private final int status;
    private final String errorCode;
    private final String errorMessage;
    private final String path;
    private final LocalDateTime timestamp;

    private ErrorResponse(int status, String errorCode, String errorMessage, String path) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public static ErrorResponse of(HttpStatus status, String errorCode, String errorMessage, String path) {
        return new ErrorResponse(status.value(), errorCode, errorMessage, path);
    }
}