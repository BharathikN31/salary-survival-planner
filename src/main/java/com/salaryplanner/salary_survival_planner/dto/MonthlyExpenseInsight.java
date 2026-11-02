package com.salaryplanner.salary_survival_planner.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class MonthlyExpenseInsight {

    private Long userId;
    private String category;
    private BigDecimal totalSpent;
    private BigDecimal percentageOfTotal;
}