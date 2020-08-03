package com.tw.calculator.response;

import java.util.Map;

public class CalculatorFailureResponse {
    private String message;
    private Map<String, String> reasons;

    public CalculatorFailureResponse(String message, Map<String, String> reasons) {
        this.message = message;
        this.reasons = reasons;
    }

    public CalculatorFailureResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getReasons() {
        return reasons;
    }

    public void setReasons(Map<String, String> reasons) {
        this.reasons = reasons;
    }

    public CalculatorFailureResponse message(String message) {
        this.message = message;
        return this;
    }

    public CalculatorFailureResponse reasons(Map<String, String> reasons) {
        this.reasons = reasons;
        return this;
    }

}
