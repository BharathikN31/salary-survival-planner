package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.RecurringPayment;
import com.salaryplanner.salary_survival_planner.service.RecurringPaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recurring-payments")
public class RecurringPaymentController {

    @Autowired
    private RecurringPaymentService recurringPaymentService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<RecurringPayment>> getUpcomingRecurringPayments(
            @PathVariable Long userId) {

        List<RecurringPayment> payments =
                recurringPaymentService.getUpcomingRecurringPayments(userId);

        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
}