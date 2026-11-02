package com.salaryplanner.salary_survival_planner.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SavingsContributionPlan {

    private Long userId;
    private Long goalId;
    private String goalName;

    private BigDecimal targetAmount;
    private BigDecimal investedAmount;
    private BigDecimal remainingAmount;

    private LocalDate targetDate;
    private Long daysRemaining;
    private Long monthsRemaining;

    private BigDecimal requiredDailyContribution;
    private BigDecimal requiredMonthlyContribution;

    private String planStatus;
}