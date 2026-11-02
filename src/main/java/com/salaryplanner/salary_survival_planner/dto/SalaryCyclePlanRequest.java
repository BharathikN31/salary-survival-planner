package com.salaryplanner.salary_survival_planner.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalaryCyclePlanRequest {

    @NotNull(message = "Planned savings is required")
    @PositiveOrZero(message = "Planned savings cannot be negative")
    private BigDecimal plannedSavings;
}