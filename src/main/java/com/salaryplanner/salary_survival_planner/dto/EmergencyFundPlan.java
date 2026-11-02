package com.salaryplanner.salary_survival_planner.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmergencyFundPlan {

    private Long userId;

    private BigDecimal monthlyEssentialExpenses;

    private Integer targetMonths;

    private BigDecimal recommendedFund;

    private BigDecimal currentSavings;

    private BigDecimal remainingAmount;

    private BigDecimal completionPercentage;

    private String fundStatus;
}