package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.FinancialDashboard;
import com.salaryplanner.salary_survival_planner.service.FinancialDashboardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class FinancialDashboardController {

    @Autowired
    private FinancialDashboardService financialDashboardService;


    @GetMapping("/{userId}")
    public ResponseEntity<FinancialDashboard> getDashboard(
            @PathVariable Long userId) {

        FinancialDashboard dashboard =
                financialDashboardService.getDashboard(userId);

        return ResponseEntity.ok(dashboard);
    }
}