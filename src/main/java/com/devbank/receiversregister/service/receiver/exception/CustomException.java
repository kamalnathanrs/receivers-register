package com.devbank.receiversregister.service.receiver.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    @Getter
    private final HttpStatus httpStatus;

    @Getter
    private final String msg;

    public CustomException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.msg = message;
    }
}
