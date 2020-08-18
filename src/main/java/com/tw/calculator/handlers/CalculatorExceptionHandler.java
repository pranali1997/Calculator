package com.tw.calculator.handlers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.tw.calculator.errorCodes.InternalErrorCodes;
import com.tw.calculator.exceptions.DivisionNotPossibleException;
import com.tw.calculator.response.CalculatorFailureResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class CalculatorExceptionHandler {

    //    public void logException(Exception ex){
//        InternalErrorCodes errorCode= InternalErrorCodes.valueOf("unexpected exception");
//
//        log.error("Exception occurred------->",kv("error code",errorCode.getCode()));
//    }
    @ExceptionHandler(DivisionNotPossibleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CalculatorFailureResponse divisionExceptionHandle(DivisionNotPossibleException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getInternalErrorCodes().toString(), ex.getErrorMessage());
        return new CalculatorFailureResponse().message(ex.getInternalErrorCodes().toString()).reasons(errors);
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CalculatorFailureResponse handlingInvalidInput(InvalidFormatException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(InternalErrorCodes.INVALID_INPUT.toString(), "Input values must be in numbers only");
        return new CalculatorFailureResponse().message("INVALID_INPUT").reasons(errors);
    }
}
