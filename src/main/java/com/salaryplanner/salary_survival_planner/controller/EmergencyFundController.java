package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.EmergencyFundPlan;
import com.salaryplanner.salary_survival_planner.entity.EmergencyFund;
import com.salaryplanner.salary_survival_planner.service.EmergencyFundService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emergency-funds")
public class EmergencyFundController {

    @Autowired
    private EmergencyFundService emergencyFundService;

    @PostMapping
    public ResponseEntity<EmergencyFund> saveEmergencyFund(
            @Valid @RequestBody EmergencyFund fund) {

        EmergencyFund savedFund =
                emergencyFundService.saveEmergencyFund(fund);

        return new ResponseEntity<>(savedFund, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<EmergencyFundPlan> getEmergencyFundPlan(
            @PathVariable Long userId) {

        EmergencyFundPlan plan =
                emergencyFundService.calculateEmergencyFund(userId);

        return new ResponseEntity<>(plan, HttpStatus.OK);
    }
}