package com.salaryplanner.salary_survival_planner.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
@Entity
@Table(name = "monthly_savings_targets")
@Data
public class MonthlySavingsTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be positive")
    private Long userId;

    @NotNull(message = "Target year is required")
    @Min(value = 2020, message = "Target year is invalid")
    @Max(value = 2100, message = "Target year is invalid")
    private Integer targetYear;

    @NotNull(message = "Target month is required")
    @Min(value = 1, message = "Month must be between 1 and 12")
    @Max(value = 12, message = "Month must be between 1 and 12")
    private Integer targetMonth;

    @NotNull(message = "Target amount is required")
    @Positive(message = "Target amount must be greater than zero")
    private BigDecimal targetAmount;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}