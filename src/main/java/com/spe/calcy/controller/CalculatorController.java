package com.spe.calcy.controller;

import com.spe.calcy.model.CalculatorOperation;
import com.spe.calcy.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @ModelAttribute("operations")
    public CalculatorOperation[] getOperations() {
        return CalculatorOperation.values();
    }

    @GetMapping
    public String showCalculator(Model model) {
        return "calculator";
    }

    @PostMapping
    public String calculate(@RequestParam("operation") CalculatorOperation operation,
                            @RequestParam("operand") double operand,
                            @RequestParam(value = "secondOperand", required = false, defaultValue = "0") double secondOperand,
                            Model model) {
        double result = calculatorService.performOperation(operation, operand, secondOperand);
        model.addAttribute("result", result);
        return "calculator";
    }
}
