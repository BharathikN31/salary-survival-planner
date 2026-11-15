
package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.entity.InvestmentTransaction;
import com.salaryplanner.salary_survival_planner.service.InvestmentTransactionService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/investment-transactions")
@Validated
public class InvestmentTransactionController {

    @Autowired
    private InvestmentTransactionService transactionService;

    @PostMapping
    public ResponseEntity<InvestmentTransaction> addTransaction(
            @Valid @RequestBody InvestmentTransaction transaction) {

        InvestmentTransaction savedTransaction =
                transactionService.addTransaction(transaction);

        return new ResponseEntity<>(
                savedTransaction, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InvestmentTransaction>> getUserTransactions() {

        return new ResponseEntity<>(
                transactionService.getUserTransactions(),
                HttpStatus.OK);
    }

    @GetMapping("/goal/{goalId}")
    public ResponseEntity<List<InvestmentTransaction>> getGoalTransactions(
            @PathVariable Long goalId) {

        return new ResponseEntity<>(
                transactionService.getGoalTransactions(goalId),
                HttpStatus.OK);
    }

    @GetMapping("/monthly")
    public ResponseEntity<List<InvestmentTransaction>> getMonthlyTransactions(
            @RequestParam
            @Min(value = 2020, message = "Year must be 2020 or later")
            @Max(value = 2100, message = "Year cannot exceed 2100")
            int year,

            @RequestParam
            @Min(value = 1, message = "Month must be between 1 and 12")
            @Max(value = 12, message = "Month must be between 1 and 12")
            int month) {

        return new ResponseEntity<>(
                transactionService.getMonthlyTransactions(year, month),
                HttpStatus.OK);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(
            @PathVariable Long transactionId) {

        transactionService.deleteTransaction(transactionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
