package ru.aclij.webacl.controllers.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class AppError {
    private final int status;
    private final String message;
    private final Date timestamp;

    public AppError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}

