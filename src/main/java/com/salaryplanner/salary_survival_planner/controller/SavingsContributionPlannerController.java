package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.SavingsContributionPlan;
import com.salaryplanner.salary_survival_planner.service.SavingsContributionPlannerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/savings-contribution")
public class SavingsContributionPlannerController {

    @Autowired
    private SavingsContributionPlannerService contributionPlannerService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<SavingsContributionPlan>> getContributionPlans(
            @PathVariable Long userId) {

        List<SavingsContributionPlan> plans =
                contributionPlannerService.getContributionPlans(userId);

        return new ResponseEntity<>(plans, HttpStatus.OK);
    }
}