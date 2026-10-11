package com.salaryplanner.salary_survival_planner.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class MonthlyFinancialReport {

    private Long userId;
    private int month;
    private int year;

    private BigDecimal totalIncome;
    private BigDecimal totalCommitments;
    private BigDecimal totalExpenses;
    private BigDecimal totalInvested;
    private BigDecimal totalBudgetAllocated;
    private BigDecimal remainingBalance;
    private BigDecimal budgetUtilizationPercentage;
}