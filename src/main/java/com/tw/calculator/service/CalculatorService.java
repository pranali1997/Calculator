package com.tw.calculator.service;

import com.tw.calculator.errorCodes.InternalErrorCodes;
import com.tw.calculator.exceptions.DivisionNotPossibleException;
import com.tw.calculator.model.InputRequest;
import com.tw.calculator.repository.CalculatorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculatorService {

    private final CalculatorRepository calculatorRepository;

    public CalculatorService(CalculatorRepository calculatorRepository) {
        this.calculatorRepository = calculatorRepository;
    }

    public int addition(InputRequest inputRequest) {
        int result = inputRequest.getNumber1() + inputRequest.getNumber2();
        inputRequest.setResult(result);
        inputRequest.setCalcType("+");
        calculatorRepository.save(inputRequest);
        return result;
    }

    public int subtraction(InputRequest inputRequest) {
        int result = inputRequest.getNumber1() - inputRequest.getNumber2();
        inputRequest.setResult(result);
        inputRequest.setCalcType("-");
        calculatorRepository.save(inputRequest);
        return result;
    }

    public List<InputRequest> getCalculation() {
        return calculatorRepository.findAll();
    }

    public int division(InputRequest inputRequest) throws DivisionNotPossibleException {
        int result = 0;
        if (inputRequest.getNumber2() == 0) {
            throw new DivisionNotPossibleException(InternalErrorCodes.CAN_NOT_BE_DIVIDED_BY_ZERO, "number can't divide by zero");
        }
        result = inputRequest.getNumber1() / inputRequest.getNumber2();
        inputRequest.setResult(result);
        inputRequest.setCalcType("%");
        calculatorRepository.save(inputRequest);
        return result;
    }
}
