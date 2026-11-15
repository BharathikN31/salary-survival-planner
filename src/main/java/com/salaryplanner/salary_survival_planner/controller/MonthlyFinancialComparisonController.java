
package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.MonthlyFinancialComparison;
import com.salaryplanner.salary_survival_planner.service.MonthlyFinancialComparisonService;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monthly-comparison")
@Validated
public class MonthlyFinancialComparisonController {

    @Autowired
    private MonthlyFinancialComparisonService comparisonService;

    @GetMapping
    public ResponseEntity<List<MonthlyFinancialComparison>> compareMonths(

            @RequestParam
            @Min(1) @Max(12) int previousMonth,

            @RequestParam
            @Min(2020) @Max(2100) int previousYear,

            @RequestParam
            @Min(1) @Max(12) int currentMonth,

            @RequestParam
            @Min(2020) @Max(2100) int currentYear) {

        List<MonthlyFinancialComparison> comparisons =
                comparisonService.compareMonths(
                        previousMonth,
                        previousYear,
                        currentMonth,
                        currentYear
                );

        return new ResponseEntity<>(comparisons, HttpStatus.OK);
    }
}
