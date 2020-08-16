package com.tw.calculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tw.calculator.exceptions.DivisionNotPossibleException;
import com.tw.calculator.model.InputRequest;
import com.tw.calculator.service.CalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.kv;

@RestController
@Slf4j
@RequestMapping("/calculator")
public class CalculatorController {

    CalculatorService calculatorService;

    @Autowired
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    String number1="number1", number2="number2", result="result", eventCode="EventCode", details="details",divisionSuccessful="divisionSuccessful";

    @PostMapping("/sum")
    public ResponseEntity sum(@RequestBody InputRequest inputRequest) {
        int addition = calculatorService.addition(inputRequest);
        ObjectNode mapper = new ObjectMapper().createObjectNode();
        mapper.put(number1, inputRequest.getNumber1());
        mapper.put(number2, inputRequest.getNumber2());
        mapper.put(result, inputRequest.getResult());
        log.info("successful summation--->", kv(eventCode, divisionSuccessful), kv(details, mapper.toString()));
        return new ResponseEntity(addition, HttpStatus.OK);
    }

    @PostMapping("/sub")
    public ResponseEntity sub(@RequestBody InputRequest inputRequest) {
        int subtraction = calculatorService.subtraction(inputRequest);
        ObjectNode mapper = new ObjectMapper().createObjectNode();
        mapper.put(number1, inputRequest.getNumber1());
        mapper.put(number2, inputRequest.getNumber2());
        mapper.put(result, inputRequest.getResult());
        log.info("successful subtraction--->", kv(eventCode, divisionSuccessful), kv(details, mapper.toString()));
        return new ResponseEntity(subtraction, HttpStatus.OK);
    }

    @PostMapping("/div")
    public ResponseEntity div(@RequestBody InputRequest inputRequest) throws DivisionNotPossibleException, NoSuchFieldException {
        int division = calculatorService.division(inputRequest);
        ObjectNode mapper = new ObjectMapper().createObjectNode();
        mapper.put(number1, inputRequest.getNumber1());
        mapper.put(number2, inputRequest.getNumber2());
        mapper.put(result, inputRequest.getResult());
        log.info("successful division--->", kv(eventCode, divisionSuccessful), kv(details, mapper.toString()));
        return new ResponseEntity(division, HttpStatus.OK);
    }

    @GetMapping("/history")
    public List<InputRequest> getCalculation() {
        return calculatorService.getCalculation();
    }
}
