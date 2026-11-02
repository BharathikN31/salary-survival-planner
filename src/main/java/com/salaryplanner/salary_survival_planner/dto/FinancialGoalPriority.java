package com.salaryplanner.salary_survival_planner.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FinancialGoalPriority {

    private Long userId;
    private Long goalId;
    private String goalName;

    private BigDecimal targetAmount;
    private BigDecimal investedAmount;
    private BigDecimal remainingAmount;

    private LocalDate targetDate;
    private Long daysRemaining;

    private BigDecimal requiredMonthlyContribution;

    private Integer priorityScore;
    private String priorityLevel;
}