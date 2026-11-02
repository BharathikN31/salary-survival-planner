package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.MonthlyExpenseInsight;
import com.salaryplanner.salary_survival_planner.service.MonthlyExpenseInsightService;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense-insights")
@Validated
public class MonthlyExpenseInsightController {

    @Autowired
    private MonthlyExpenseInsightService monthlyExpenseInsightService;

    @GetMapping("/{userId}/{year}/{month}")
    public ResponseEntity<List<MonthlyExpenseInsight>> getMonthlyExpenseInsights(
            @PathVariable Long userId,
            @PathVariable @Min(2020) @Max(2100) int year,
            @PathVariable @Min(1) @Max(12) int month) {

        List<MonthlyExpenseInsight> insights =
                monthlyExpenseInsightService.getMonthlyExpenseInsights(
                        userId, month, year
                );

        return new ResponseEntity<>(insights, HttpStatus.OK);
    }
}