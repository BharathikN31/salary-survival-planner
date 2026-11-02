package com.salaryplanner.salary_survival_planner.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentReminder {

    private Long userId;
    private Long commitmentId;
    private String paymentName;
    private BigDecimal amount;
    private LocalDate dueDate;
    private Long daysRemaining;
    private String reminderStatus;
}