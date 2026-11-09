package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.entity.Budget;
import com.salaryplanner.salary_survival_planner.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    // Add budget
    @PostMapping
    public ResponseEntity<Budget> addBudget(
            @Valid @RequestBody Budget newBudget) {

        return new ResponseEntity<>(
                budgetService.addBudget(newBudget),
                HttpStatus.CREATED
        );
    }

    // Get budget by ID
    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable Long id) {

        return new ResponseEntity<>(
                budgetService.getBudgetById(id),
                HttpStatus.OK
        );
    }

    // Get all budgets
    @GetMapping
    public ResponseEntity<List<Budget>> getAllBudgets() {

        return new ResponseEntity<>(
                budgetService.getAllBudgets(),
                HttpStatus.OK
        );
    }

    // Delete budget
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {

        budgetService.deleteBudget(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Update budget
    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudget(
            @PathVariable Long id,
            @Valid @RequestBody Budget updateBudget) {

        return new ResponseEntity<>(
                budgetService.updateBudget(id, updateBudget),
                HttpStatus.OK
        );
    }
}