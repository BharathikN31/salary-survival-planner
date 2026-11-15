
package com.salaryplanner.salary_survival_planner.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmergencyFundRequest {

    @NotNull(message = "Monthly essential expenses are required")
    @Positive(message = "Monthly essential expenses must be greater than zero")
    private BigDecimal monthlyEssentialExpenses;

    @NotNull(message = "Target months are required")
    @Min(value = 1, message = "Target must be at least 1 month")
    @Max(value = 24, message = "Target cannot exceed 24 months")
    private Integer targetMonths;

    @NotNull(message = "Current savings are required")
    @PositiveOrZero(message = "Current savings cannot be negative")
    private BigDecimal currentSavings;
}
