package com.tw.calculator.service;

import com.tw.calculator.exceptions.DivisionNotPossibleException;
import com.tw.calculator.model.InputRequest;
import com.tw.calculator.repository.CalculatorRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculatorServiceTest {

    InputRequest values;
    CalculatorRepository calculatorRepository;

    @BeforeEach
    void setUp() {
        calculatorRepository = mock(CalculatorRepository.class);
        values = new InputRequest(5, 4);
    }

    @Test
    void givesAdditionOfTwoNumbers() {
        CalculatorService calculatorService = new CalculatorService(calculatorRepository);
        when(calculatorRepository.save(any())).thenReturn(values);
        int addition = calculatorService.addition(values);
        assertThat(addition, is(9));
    }

    @Test
    void givesSubtraction() {
        CalculatorService calculatorService = new CalculatorService(calculatorRepository);
        when(calculatorRepository.save(any())).thenReturn(values);
        int subtraction = calculatorService.subtraction(values);
        assertThat(subtraction, is(1));
    }

    @Test
    void givesDivision() throws DivisionNotPossibleException {
        CalculatorService calculatorService = new CalculatorService(calculatorRepository);
        when(calculatorRepository.save(any())).thenReturn(values);
        int division = calculatorService.division(values);
        assertThat(division, is(1));
    }

    @Test
    void givesDivisionByZero() {
        calculatorRepository = mock(CalculatorRepository.class);
        values = new InputRequest(5, 0);
        CalculatorService calculatorService = new CalculatorService(calculatorRepository);
        when(calculatorRepository.save(any())).thenReturn(values);
        DivisionNotPossibleException divisionNotPossibleException = assertThrows(DivisionNotPossibleException.class, () -> calculatorService.division(values));
        assertThat(divisionNotPossibleException.getMessage(), Matchers.is("number can't divide by zero"));
    }

}
