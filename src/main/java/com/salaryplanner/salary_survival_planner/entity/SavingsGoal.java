package com.salaryplanner.salary_survival_planner.entity;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class SavingsGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @NotBlank(message = "Goal name is required")
    @Size(max = 100, message = "Goal name cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull(message = "Target amount is required")
    @Positive(message = "Target amount must be greater than zero")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @NotNull(message = "Invested amount is required")
    @PositiveOrZero(message = "Invested amount cannot be negative")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal investedAmount;

    @NotNull(message = "Target date is required")
    @Column(nullable = false)
    private LocalDate targetDate;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}