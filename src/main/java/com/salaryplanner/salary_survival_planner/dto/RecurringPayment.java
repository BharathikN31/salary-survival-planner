package com.salaryplanner.salary_survival_planner.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RecurringPayment {

    private Long userId;
    private String paymentName;
    private BigDecimal amount;
    private LocalDate dueDate;
    private Long daysUntilDue;
    private String paymentStatus;
}