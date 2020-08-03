package com.tw.calculator.model;

import javax.persistence.*;

@Entity
@Table(name = "calculator")
public class InputRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int number1;
    private int number2;
    private int result;
    private String calcType;

    public InputRequest(int number1, int number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    public InputRequest() {
    }

    public int getNumber1() {
        return number1;
    }

    public int getNumber2() {
        return number2;
    }

    public int getResult() {
        return result;
    }

    public String getCalcType() {
        return calcType;
    }

    public int getId() {
        return id;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setCalcType(String calcType) {
        this.calcType = calcType;
    }
}
