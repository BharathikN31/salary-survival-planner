package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.DailySpendingSummary;
import com.salaryplanner.salary_survival_planner.service.DailySpendingTrackerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/daily-spending")
public class DailySpendingTrackerController {

    @Autowired
    private DailySpendingTrackerService dailySpendingTrackerService;

    @GetMapping("/{userId}")
    public ResponseEntity<DailySpendingSummary> getDailySpendingSummary(
            @PathVariable Long userId,
            @RequestParam LocalDate date) {

        DailySpendingSummary summary =
                dailySpendingTrackerService.getDailySpendingSummary(
                        userId, date);

        return new ResponseEntity<>(summary, HttpStatus.OK);
    }
}