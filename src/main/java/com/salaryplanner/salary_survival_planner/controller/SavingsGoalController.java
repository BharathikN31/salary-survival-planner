package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.entity.SavingsGoal;
import com.salaryplanner.salary_survival_planner.service.SavingsGoalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/savings-goals")
public class SavingsGoalController {

    @Autowired
    private SavingsGoalService savingsGoalService;

    // Add savings goal
    @PostMapping
    public ResponseEntity<SavingsGoal> addSavingsGoal(
            @Valid @RequestBody SavingsGoal newGoal) {

        return new ResponseEntity<>(
                savingsGoalService.addSavingsGoal(newGoal),
                HttpStatus.CREATED
        );
    }

    // Get savings goal by ID
    @GetMapping("/{id}")
    public ResponseEntity<SavingsGoal> getSavingsGoalById(
            @PathVariable Long id) {

        return new ResponseEntity<>(
                savingsGoalService.getSavingsGoalById(id),
                HttpStatus.OK
        );
    }

    // Get all savings goals
    @GetMapping
    public ResponseEntity<List<SavingsGoal>> getAllSavingsGoals() {

        return new ResponseEntity<>(
                savingsGoalService.getAllSavingsGoals(),
                HttpStatus.OK
        );
    }

    // Delete savings goal
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSavingsGoal(@PathVariable Long id) {

        savingsGoalService.deleteSavingsGoal(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Update savings goal
    @PutMapping("/{id}")
    public ResponseEntity<SavingsGoal> updateSavingsGoal(
            @PathVariable Long id,
            @Valid @RequestBody SavingsGoal modifyGoal) {

        return new ResponseEntity<>(
                savingsGoalService.updateSavingsGoal(id, modifyGoal),
                HttpStatus.OK
        );
    }
}