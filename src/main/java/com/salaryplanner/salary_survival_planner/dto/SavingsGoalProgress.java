package com.salaryplanner.salary_survival_planner.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SavingsGoalProgress {

    private Long userId;
    private Long goalId;
    private String goalName;

    private BigDecimal totalAmount;
    private BigDecimal investedAmount;
    private BigDecimal remainingAmount;

    private BigDecimal completionPercentage;
    private String progressStatus;
}