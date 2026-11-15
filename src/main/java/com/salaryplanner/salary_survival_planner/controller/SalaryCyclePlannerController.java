
package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.SalaryCyclePlan;
import com.salaryplanner.salary_survival_planner.dto.SalaryCyclePlanRequest;
import com.salaryplanner.salary_survival_planner.service.SalaryCyclePlannerService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/salary-cycle")
public class SalaryCyclePlannerController {

    @Autowired
    private SalaryCyclePlannerService salaryCyclePlannerService;

    @PostMapping
    public ResponseEntity<SalaryCyclePlan> createSalaryCyclePlan(
            @Valid @RequestBody SalaryCyclePlanRequest request) {

        SalaryCyclePlan plan =
                salaryCyclePlannerService.createSalaryCyclePlan(
                        request.getPlannedSavings()
                );

        return ResponseEntity.ok(plan);
    }
}
