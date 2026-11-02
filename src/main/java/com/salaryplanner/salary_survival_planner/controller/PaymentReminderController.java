package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.PaymentReminder;
import com.salaryplanner.salary_survival_planner.service.PaymentReminderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-reminders")
public class PaymentReminderController {

    @Autowired
    private PaymentReminderService paymentReminderService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<PaymentReminder>> getPaymentReminders(
            @PathVariable Long userId) {

        List<PaymentReminder> reminders =
                paymentReminderService.getPaymentReminders(userId);

        return new ResponseEntity<>(reminders, HttpStatus.OK);
    }
}