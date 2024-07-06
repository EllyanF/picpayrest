package com.ellyanf.picpayrest.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetails {
    private String message;
    private String details;
    private Date timestamp;

    public ErrorDetails(String message, String details) {
        this.message = message;
        this.details = details;
        this.timestamp = new Date();
    }
}
