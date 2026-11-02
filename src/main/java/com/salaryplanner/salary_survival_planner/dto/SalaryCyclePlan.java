package com.salaryplanner.salary_survival_planner.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SalaryCyclePlan {

    private Long userId;
    private BigDecimal salaryAmount;
    private BigDecimal totalCommitments;
    private BigDecimal plannedSavings;
    private BigDecimal availableForExpenses;
    private BigDecimal dailySpendingLimit;
    private Long daysInSalaryCycle;
}