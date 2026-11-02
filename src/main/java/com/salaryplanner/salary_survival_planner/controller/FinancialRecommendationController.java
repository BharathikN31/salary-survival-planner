package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.FinancialRecommendation;
import com.salaryplanner.salary_survival_planner.service.FinancialRecommendationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class FinancialRecommendationController {

    @Autowired
    private FinancialRecommendationService financialRecommendationService;


    @GetMapping("/{userId}")
    public ResponseEntity<List<FinancialRecommendation>> getRecommendations(
            @PathVariable Long userId) {

        List<FinancialRecommendation> recommendations =
                financialRecommendationService.getRecommendations(userId);

        return ResponseEntity.ok(recommendations);
    }
}