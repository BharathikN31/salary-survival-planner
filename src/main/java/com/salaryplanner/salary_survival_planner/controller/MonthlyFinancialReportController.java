package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.MonthlyFinancialReport;
import com.salaryplanner.salary_survival_planner.service.MonthlyFinancialReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class MonthlyFinancialReportController {

    @Autowired
    private MonthlyFinancialReportService reportService;

    @GetMapping("/{userId}/{year}/{month}")
    public ResponseEntity<MonthlyFinancialReport> getMonthlyReport(
            @PathVariable Long userId,
            @PathVariable int year,
            @PathVariable int month) {

        MonthlyFinancialReport report =
                reportService.generateMonthlyReport(userId, month, year);

        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}