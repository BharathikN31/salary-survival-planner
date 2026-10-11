package com.salaryplanner.salary_survival_planner.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OverspendingAlert {

    private Long userId;
    private String category;
    private BigDecimal monthlyLimit;
    private BigDecimal totalSpent;
    private BigDecimal overspentAmount;
    private String alertMessage;
}