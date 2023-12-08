package com.engineerLee.gatewaymanagement.exception;

import lombok.Data;

@Data
public class DataIntegrityViolationException extends RuntimeException {

    private String message;

    public DataIntegrityViolationException() {
        super();
        this.message = "data integrity has been bridged";
    }
    public DataIntegrityViolationException(String msg) {
        super(msg);
        this.message = msg;
    }
}
