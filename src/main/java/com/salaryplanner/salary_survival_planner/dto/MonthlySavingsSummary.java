package com.salaryplanner.salary_survival_planner.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class MonthlySavingsSummary {

    private Long userId;

    private Integer year;

    private Integer month;

    private BigDecimal targetAmount;

    private BigDecimal totalContributions;

    private BigDecimal totalWithdrawals;

    private BigDecimal netSavings;

    private BigDecimal remainingAmount;

    private BigDecimal completionPercentage;

    private String status;
}