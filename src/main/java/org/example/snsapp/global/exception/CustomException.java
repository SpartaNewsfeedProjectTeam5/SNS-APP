package org.example.snsapp.global.exception;

import org.example.snsapp.global.enums.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 커스텀 예외
 * <p>{@link RuntimeException}를 상속받고 {@link ErrorCode}를 생성자로 받아 예외를 발생시킨다.</p>
 */

public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public String getCode() {
        return errorCode.getCode();
    }

    public HttpStatus getStatus() {
        return errorCode.getStatus();
    }
}