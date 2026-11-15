
package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.EmergencyFundPlan;
import com.salaryplanner.salary_survival_planner.dto.EmergencyFundRequest;
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
            @Valid @RequestBody EmergencyFundRequest request) {

        EmergencyFund savedFund =
                emergencyFundService.saveEmergencyFund(request);

        return new ResponseEntity<>(savedFund, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<EmergencyFundPlan> getEmergencyFundPlan() {

        EmergencyFundPlan plan =
                emergencyFundService.calculateEmergencyFund();

        return new ResponseEntity<>(plan, HttpStatus.OK);
    }
}
