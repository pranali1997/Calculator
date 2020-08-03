package com.tw.calculator.repository;

import com.tw.calculator.model.InputRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculatorRepository extends JpaRepository<InputRequest, Integer> {
}
