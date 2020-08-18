package com.tw.calculator.service;

import com.tw.calculator.exceptions.DivisionNotPossibleException;
import com.tw.calculator.model.InputRequest;
import com.tw.calculator.repository.CalculatorRepository;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class CalculatorCircuitBreaker {


    @Autowired
    CircuitBreakerRegistry circuitBreakerRegistry;

    @Autowired
    RetryRegistry retryRegistry;

    @MockBean
    CalculatorService calculatorService;

    InputRequest values;
    CalculatorRepository calculatorRepository;
    @BeforeEach
    void circuitBreakerSetup() {
        circuitBreakerRegistry.circuitBreaker("calculatorservice").reset();
    }

    @BeforeEach
    void setUp() {
        calculatorRepository = mock(CalculatorRepository.class);
        values = new InputRequest(5, 4);
    }

    @AfterEach
    void clearMDC() {
        MDC.clear();
    }


    @Test
    public void circuitBreakerChangesItsStateFromOpenToClosed() throws Exception {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("calculatorservice");
//        stubFor(get(urlEqualTo("/checkDetails?accountNumber=12345&ifscCode=HDFC1234")).willReturn(aResponse().withStatus(200)));
        when(calculatorService.addition(values)).thenReturn(9);

        doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
                .doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
                .doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
                .doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
                .doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
                .doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
                .doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
                .doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
                .doNothing().doNothing()
                .doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
                .doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
                .doNothing().doNothing().doNothing().doNothing().doNothing()
                .when(calculatorRepository).save(any());

        when(calculatorRepository.save(any())).thenReturn(values);

        assertThrows(DivisionNotPossibleException.class, () -> calculatorService.addition(values));
        verify(calculatorRepository, times(3)).save(any());
        assertEquals("CLOSED", circuitBreaker.getState().name());

        assertThrows(CallNotPermittedException.class, () -> calculatorService.addition(values));
        verify(calculatorRepository, times(4)).save(any());
        assertEquals("OPEN", circuitBreaker.getState().name());

        TimeUnit.SECONDS.sleep(5);
        assertThrows(DivisionNotPossibleException.class, () -> calculatorService.addition(values));
        assertEquals("HALF_OPEN", circuitBreaker.getState().name());

        assertThrows(CallNotPermittedException.class, () -> calculatorService.addition(values));
        verify(calculatorRepository, times(8)).save(any());
        assertEquals("OPEN", circuitBreaker.getState().name());


        TimeUnit.SECONDS.sleep(5);
        assertEquals(calculatorService.addition(values),9);
        assertEquals("HALF_OPEN", circuitBreaker.getState().name());

        assertEquals(calculatorService.addition(values),9);
        assertThrows(CallNotPermittedException.class, () ->calculatorService.addition(values));
        verify(calculatorRepository, times(12)).save(any());
        assertEquals("OPEN", circuitBreaker.getState().name());

        TimeUnit.SECONDS.sleep(5);
        assertEquals(calculatorService.addition(values),9);
        assertEquals("HALF_OPEN", circuitBreaker.getState().name());

        assertEquals(calculatorService.addition(values),9);
        assertEquals(calculatorService.addition(values),9);
        assertEquals(calculatorService.addition(values),9);
        assertEquals("CLOSED", circuitBreaker.getState().name());

        assertEquals(calculatorService.addition(values),9);
        verify(calculatorRepository, times(17)).save(any());
        assertEquals("CLOSED", circuitBreaker.getState().name());

    }

}
