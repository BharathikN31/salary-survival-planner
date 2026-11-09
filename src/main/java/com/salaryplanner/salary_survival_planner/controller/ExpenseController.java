package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.entity.Expense;
import com.salaryplanner.salary_survival_planner.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    // Add expense
    @PostMapping
    public ResponseEntity<Expense> addExpense(
            @Valid @RequestBody Expense newExpense) {

        return new ResponseEntity<>(
                expenseService.addExpense(newExpense),
                HttpStatus.CREATED
        );
    }

    // Get expense by ID
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {

        return new ResponseEntity<>(
                expenseService.getExpenseById(id),
                HttpStatus.OK
        );
    }

    // Get all expenses
    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {

        return new ResponseEntity<>(
                expenseService.getAllExpenses(),
                HttpStatus.OK
        );
    }

    // Delete expense
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {

        expenseService.deleteExpense(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Update expense
    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody Expense updateExpense) {

        return new ResponseEntity<>(
                expenseService.updateExpense(id, updateExpense),
                HttpStatus.OK
        );
    }
}