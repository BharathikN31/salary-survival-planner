
package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.SavingsContributionPlan;
import com.salaryplanner.salary_survival_planner.service.SavingsContributionPlannerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/savings-contribution")
public class SavingsContributionPlannerController {

    @Autowired
    private SavingsContributionPlannerService contributionPlannerService;

    @GetMapping
    public ResponseEntity<List<SavingsContributionPlan>> getContributionPlans() {

        List<SavingsContributionPlan> plans =
                contributionPlannerService.getContributionPlans();

        return ResponseEntity.ok(plans);
    }
}
