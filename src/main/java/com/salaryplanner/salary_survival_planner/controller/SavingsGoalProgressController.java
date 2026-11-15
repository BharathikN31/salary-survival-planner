
package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.SavingsGoalProgress;
import com.salaryplanner.salary_survival_planner.service.SavingsGoalProgressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/savings-progress")
public class SavingsGoalProgressController {

    @Autowired
    private SavingsGoalProgressService savingsGoalProgressService;

    @GetMapping
    public ResponseEntity<List<SavingsGoalProgress>> getSavingsGoalProgress() {

        List<SavingsGoalProgress> progress =
                savingsGoalProgressService.getSavingsGoalProgress();

        return new ResponseEntity<>(progress, HttpStatus.OK);
    }
}
