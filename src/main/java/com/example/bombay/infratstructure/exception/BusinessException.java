package com.example.bombay.infratstructure.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    private final Integer errorCode;

    public BusinessException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
