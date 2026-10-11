package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.FinancialSummary;
import com.salaryplanner.salary_survival_planner.service.FinancialSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/summary")
public class FinancialSummaryController {

    @Autowired
    private FinancialSummaryService financialSummaryService;

    @GetMapping("/{userId}")
    public ResponseEntity<FinancialSummary> getFinancialSummary(@PathVariable Long userId){
        return new ResponseEntity<>(financialSummaryService.getFinancialSummary(userId), HttpStatus.OK);
    }
}
