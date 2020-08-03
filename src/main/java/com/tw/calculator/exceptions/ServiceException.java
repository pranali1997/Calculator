package com.tw.calculator.exceptions;

public class ServiceException extends Exception {

    private final String errorMessage;


    public ServiceException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;

    }


    public String getErrorMessage() {
        return errorMessage;
    }
}
