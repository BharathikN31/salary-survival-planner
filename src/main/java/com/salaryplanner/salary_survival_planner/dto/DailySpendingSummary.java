package com.salaryplanner.salary_survival_planner.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DailySpendingSummary {

    private Long userId;
    private LocalDate spendingDate;

    private BigDecimal safeDailyLimit;
    private BigDecimal totalSpent;
    private BigDecimal remainingLimit;
    private BigDecimal exceededAmount;

    private String spendingStatus;
}