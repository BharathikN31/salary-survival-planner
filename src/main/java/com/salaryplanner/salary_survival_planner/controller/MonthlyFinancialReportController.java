
package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.MonthlyFinancialReport;
import com.salaryplanner.salary_survival_planner.service.MonthlyFinancialReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/monthly-reports")
public class MonthlyFinancialReportController {

    @Autowired
    private MonthlyFinancialReportService reportService;

    @GetMapping("/{year}/{month}")
    public ResponseEntity<MonthlyFinancialReport> getMonthlyReport(
            @PathVariable int year,
            @PathVariable int month) {

        MonthlyFinancialReport report =
                reportService.generateMonthlyReport(month, year);

        return ResponseEntity.ok(report);
    }
}
