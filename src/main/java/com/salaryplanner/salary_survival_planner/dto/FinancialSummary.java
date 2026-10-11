package com.salaryplanner.salary_survival_planner.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FinancialSummary {
    private Long userId;
    private BigDecimal totalSalary;
    private BigDecimal totalCommitments;
    private BigDecimal totalExpenses;
    private BigDecimal totalBudgetAllocated;
    private BigDecimal totalInvested;
    private BigDecimal remainingBalance;
    private BigDecimal safeDailyLimit;
    private Long daysUntilSalary;
}
