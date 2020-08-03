package com.tw.calculator.exceptions;

import com.tw.calculator.errorCodes.InternalErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DivisionNotPossibleException extends ServiceException {

    private InternalErrorCodes internalErrorCodes;
    public String errorMessage;

    public DivisionNotPossibleException(InternalErrorCodes internalErrorCodes, String errorMessage) {
        super(errorMessage);
        this.internalErrorCodes = internalErrorCodes;
        this.errorMessage = errorMessage;

    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public InternalErrorCodes getInternalErrorCodes() {
        return internalErrorCodes;
    }
}
