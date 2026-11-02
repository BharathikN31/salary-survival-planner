package com.salaryplanner.salary_survival_planner.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class MonthlyFinancialComparison {

    private String metricName;

    private BigDecimal previousMonthAmount;
    private BigDecimal currentMonthAmount;

    private BigDecimal difference;
    private BigDecimal percentageChange;

    private String financialTrend;
}