package com.salaryplanner.salary_survival_planner.entity;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "emergency_funds")
@Data
public class EmergencyFund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be positive")
    private Long userId;

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

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}