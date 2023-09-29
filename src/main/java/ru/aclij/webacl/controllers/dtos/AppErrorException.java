package ru.aclij.webacl.controllers.dtos;

import lombok.Getter;

@Getter
public class AppErrorException extends Exception{
    private final AppError appError;

    public AppErrorException(AppError appError) {
        this.appError = appError;
    }
}
