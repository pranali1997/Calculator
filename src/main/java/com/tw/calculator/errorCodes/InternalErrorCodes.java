package com.tw.calculator.errorCodes;

public enum InternalErrorCodes {
    CAN_NOT_BE_DIVIDED_BY_ZERO("400"),
    INVALID_INPUT("400");
    private final String code;

    InternalErrorCodes(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
