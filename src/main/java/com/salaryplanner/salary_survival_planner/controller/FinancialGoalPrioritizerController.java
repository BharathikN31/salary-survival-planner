package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.FinancialGoalPriority;
import com.salaryplanner.salary_survival_planner.service.FinancialGoalPrioritizerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goal-priorities")
public class FinancialGoalPrioritizerController {

    @Autowired
    private FinancialGoalPrioritizerService prioritizerService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<FinancialGoalPriority>> prioritizeGoals(
            @PathVariable Long userId) {

        List<FinancialGoalPriority> priorities =
                prioritizerService.prioritizeGoals(userId);

        return new ResponseEntity<>(priorities, HttpStatus.OK);
    }
}