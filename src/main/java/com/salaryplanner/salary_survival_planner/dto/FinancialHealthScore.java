package com.salaryplanner.salary_survival_planner.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class FinancialHealthScore {

    private Long userId;

    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal totalCommitments;
    private BigDecimal remainingBalance;

    private BigDecimal expenseRatio;
    private BigDecimal commitmentRatio;
    private BigDecimal savingsRatio;

    private Integer healthScore;
    private String healthCategory;

    private List<String> suggestions;
}