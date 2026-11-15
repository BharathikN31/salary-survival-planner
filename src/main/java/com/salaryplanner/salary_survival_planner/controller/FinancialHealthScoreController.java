
package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.FinancialHealthScore;
import com.salaryplanner.salary_survival_planner.service.FinancialHealthScoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/financial-health")
public class FinancialHealthScoreController {

    @Autowired
    private FinancialHealthScoreService financialHealthScoreService;

    @GetMapping
    public ResponseEntity<FinancialHealthScore> getFinancialHealthScore() {

        FinancialHealthScore result =
                financialHealthScoreService.calculateHealthScore();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
