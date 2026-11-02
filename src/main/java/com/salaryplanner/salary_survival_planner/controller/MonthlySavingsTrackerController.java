package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.MonthlySavingsSummary;
import com.salaryplanner.salary_survival_planner.entity.MonthlySavingsTarget;
import com.salaryplanner.salary_survival_planner.service.MonthlySavingsTrackerService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/monthly-savings")
@Validated
public class MonthlySavingsTrackerController {

    @Autowired
    private MonthlySavingsTrackerService monthlySavingsTrackerService;


    @PostMapping("/target")
    public ResponseEntity<MonthlySavingsTarget> saveTarget(
            @Valid @RequestBody MonthlySavingsTarget target) {

        MonthlySavingsTarget savedTarget =
                monthlySavingsTrackerService.saveTarget(target);

        return new ResponseEntity<>(
                savedTarget,
                HttpStatus.CREATED
        );
    }


    @GetMapping("/{userId}")
    public ResponseEntity<MonthlySavingsSummary> getMonthlySavingsSummary(
            @PathVariable Long userId,
            @RequestParam
            @Min(value = 2000, message = "Year must be 2000 or later")
            @Max(value = 2100, message = "Year must be 2100 or earlier")
            int year,
            @RequestParam
                    @Min(value = 1, message = "Month must be between 1 and 12")
    @Max(value = 12, message = "Month must be between 1 and 12")
    int month) {

        MonthlySavingsSummary summary =
                monthlySavingsTrackerService.getMonthlySavingsSummary(
                        userId,
                        year,
                        month
                );

        return ResponseEntity.ok(summary);
    }
}