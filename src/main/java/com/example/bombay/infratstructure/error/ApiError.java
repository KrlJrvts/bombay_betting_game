package com.example.bombay.infratstructure.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {
    private String message;
    private Integer errorCode;
}
