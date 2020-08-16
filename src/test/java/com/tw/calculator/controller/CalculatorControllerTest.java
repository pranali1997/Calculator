package com.tw.calculator.controller;

import com.google.gson.Gson;
import com.tw.calculator.CalculatorApplication;
import com.tw.calculator.model.InputRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(classes = CalculatorApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CalculatorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    Gson gson;

    String jsonInputs;
//
//    @MockBean
//    JwtDecoder jwtDecoder;

    @BeforeEach
    void setUp() {
        InputRequest values = new InputRequest(5, 3);
        jsonInputs = gson.toJson(values);
    }

    @Test
    void givesAdditionOfTwoNumbers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/calculator/sum")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInputs))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(), is("8"));
    }

    @Test
    void givesSubtractionOfTwoNumbers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/calculator/sub")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInputs))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(), is("2"));
    }

    @Test
    void givesDivisionOfTwoNumbers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/calculator/div")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInputs))
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), is("1"));
    }
}
