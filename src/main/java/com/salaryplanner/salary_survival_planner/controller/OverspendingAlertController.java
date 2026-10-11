package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.OverspendingAlert;
import com.salaryplanner.salary_survival_planner.service.OverspendingAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class OverspendingAlertController {

    @Autowired
    private OverspendingAlertService overspendingAlertService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<OverspendingAlert>> getOverspendingAlerts(
            @PathVariable Long userId) {

        return new ResponseEntity<>(
                overspendingAlertService.getOverspendingAlerts(userId),
                HttpStatus.OK
        );
    }
}