package com.spe.calcy.service;// CalculatorService.java
import com.spe.calcy.model.CalculatorOperation;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public double performOperation(CalculatorOperation operation, double operand, double secondOperand) {
        switch (operation) {
            case SQUARE_ROOT:
                return Math.sqrt(operand);
            case FACTORIAL:
                return factorial(operand);
            case NATURAL_LOGARITHM:
                return Math.log(operand);
            case POWER:
                return Math.pow(operand, secondOperand);
            default:
                throw new IllegalArgumentException("Invalid operation");
        }
    }

    private double factorial(double n) {
        if (n == 0)
            return 1;
        else
            return n * factorial(n - 1);
    }
}
