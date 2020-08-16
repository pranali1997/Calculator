package com.tw.calculator.controller;

import com.google.gson.Gson;
import com.tw.calculator.model.InputRequest;
import com.tw.calculator.service.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(CalculatorController.class)
public class CalculatorControllerMockServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculatorService calculatorService;

    @Autowired
    Gson gson;

//    @MockBean
//    JwtDecoder jwtDecoder;

    String jsonInputs;

    @BeforeEach
    void setUp() {
        InputRequest values = new InputRequest(5, 2);
        jsonInputs = gson.toJson(values);
    }

    @Test
    void summationOfTwoNumbers() throws Exception {
        when(calculatorService.addition(any(InputRequest.class))).thenReturn(7);
        MvcResult mvcResult = mockMvc.perform(post("/calculator/sum").contentType(MediaType.APPLICATION_JSON)
                .content(jsonInputs))
                .andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void subtractionOfTwoNumbers() throws Exception {
        when(calculatorService.subtraction(any(InputRequest.class))).thenReturn(3);
        MvcResult mvcResult = mockMvc.perform(post("/calculator/sub").contentType(MediaType.APPLICATION_JSON)
                .content(jsonInputs))
                .andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void divisionOfTwoNumbers() throws Exception {
        when(calculatorService.division(any(InputRequest.class))).thenReturn(1);
        MvcResult mvcResult = mockMvc.perform(post("/calculator/div").contentType(MediaType.APPLICATION_JSON)
                .content(jsonInputs))
                .andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

}
