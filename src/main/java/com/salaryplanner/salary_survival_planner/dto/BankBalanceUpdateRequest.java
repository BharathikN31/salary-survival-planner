package com.salaryplanner.salary_survival_planner.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankBalanceUpdateRequest {

    @NotNull(message = "Current balance is required")
    @PositiveOrZero(message = "Bank balance cannot be negative")
    private BigDecimal currentBalance;
}