
package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.DailySpendingSummary;
import com.salaryplanner.salary_survival_planner.service.DailySpendingTrackerService;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/daily-spending")
public class DailySpendingTrackerController {

    @Autowired
    private DailySpendingTrackerService dailySpendingTrackerService;

    @GetMapping
    public ResponseEntity<DailySpendingSummary> getDailySpendingSummary(
            @RequestParam
            @NotNull
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        DailySpendingSummary summary =
                dailySpendingTrackerService.getDailySpendingSummary(date);

        return ResponseEntity.ok(summary);
    }
}
