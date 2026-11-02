package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.entity.InvestmentTransaction;
import com.salaryplanner.salary_survival_planner.service.InvestmentTransactionService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/investment-transactions")
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InvestmentTransaction>> getUserTransactions(
            @PathVariable Long userId) {

        return new ResponseEntity<>(
                transactionService.getUserTransactions(userId),
                HttpStatus.OK);
    }

    @GetMapping("/goal/{goalId}")
    public ResponseEntity<List<InvestmentTransaction>> getGoalTransactions(
            @PathVariable Long goalId) {

        return new ResponseEntity<>(
                transactionService.getGoalTransactions(goalId),
                HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/monthly")
    public ResponseEntity<List<InvestmentTransaction>> getMonthlyTransactions(
            @PathVariable Long userId,
            @RequestParam int year,
            @RequestParam int month) {

        return new ResponseEntity<>(
                transactionService.getMonthlyTransactions(
                        userId, year, month),
                HttpStatus.OK);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(
            @PathVariable Long transactionId) {

        transactionService.deleteTransaction(transactionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}