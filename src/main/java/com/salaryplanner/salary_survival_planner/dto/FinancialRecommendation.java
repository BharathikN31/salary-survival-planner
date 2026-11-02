package com.salaryplanner.salary_survival_planner.dto;

import lombok.Data;

@Data
public class FinancialRecommendation {

    private Long userId;

    private String category;

    private String recommendation;

    private String priority;
}