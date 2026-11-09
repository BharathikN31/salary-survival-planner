
package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.FinancialSummary;
import com.salaryplanner.salary_survival_planner.service.FinancialSummaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/summary")
public class FinancialSummaryController {

    @Autowired
    private FinancialSummaryService financialSummaryService;

    @GetMapping
    public ResponseEntity<FinancialSummary> getFinancialSummary() {
        return ResponseEntity.ok(
                financialSummaryService.getFinancialSummary()
        );
    }
}
