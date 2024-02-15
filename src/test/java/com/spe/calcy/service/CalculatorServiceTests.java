package com.spe.calcy.service;

import com.spe.calcy.model.CalculatorOperation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorServiceTests {

    private CalculatorService calculatorService = new CalculatorService();

    @Test
    public void testPerformOperationSquareRoot() {
        double result = calculatorService.performOperation(CalculatorOperation.SQUARE_ROOT, 9, 0);
        assertEquals(3.0, result);
    }

    @Test
    public void testPerformOperationFactorial() {
        double result = calculatorService.performOperation(CalculatorOperation.FACTORIAL, 5, 0);
        assertEquals(120.0, result);
    }

    @Test
    public void testPerformOperationNaturalLogarithm() {
        double result = calculatorService.performOperation(CalculatorOperation.NATURAL_LOGARITHM, 10, 0);
        assertEquals(2.302585092994046, result, 0.00001);
    }

    @Test
    public void testPerformOperationPower() {
        double result = calculatorService.performOperation(CalculatorOperation.POWER, 2, 3);
        assertEquals(8.0, result);
    }

    @Test
    public void testPerformOperationInvalidOperation() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculatorService.performOperation(CalculatorOperation.INVALID_OPERATION, 0, 0);
        });
    }
}
