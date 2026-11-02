package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.SalaryCyclePlan;
import com.salaryplanner.salary_survival_planner.dto.SalaryCyclePlanRequest;
import com.salaryplanner.salary_survival_planner.service.SalaryCyclePlannerService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/salary-cycle")
@Validated
public class SalaryCyclePlannerController {

    @Autowired
    private SalaryCyclePlannerService salaryCyclePlannerService;

    @PostMapping("/{userId}")
    public ResponseEntity<SalaryCyclePlan> createSalaryCyclePlan(
            @PathVariable
            @Positive(message = "User ID must be positive")
            Long userId,

            @Valid @RequestBody SalaryCyclePlanRequest request) {

        SalaryCyclePlan plan =
                salaryCyclePlannerService.createSalaryCyclePlan(
                        userId,
                        request.getPlannedSavings()
                );

        return new ResponseEntity<>(plan, HttpStatus.OK);
    }
}