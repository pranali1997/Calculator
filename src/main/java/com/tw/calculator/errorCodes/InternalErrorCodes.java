package com.tw.calculator.errorCodes;

public enum InternalErrorCodes {
    CAN_NOT_DIVISIBLE_BY_ZERO("500");

    private final String code;

    InternalErrorCodes(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
