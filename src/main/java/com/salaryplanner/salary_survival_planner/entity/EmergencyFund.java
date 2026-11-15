
package com.salaryplanner.salary_survival_planner.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "emergency_funds")
@Data
public class EmergencyFund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @NotNull(message = "Monthly essential expenses are required")
    @Positive(message = "Monthly essential expenses must be greater than zero")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal monthlyEssentialExpenses;

    @NotNull(message = "Target months are required")
    @Positive(message = "Target months must be greater than zero")
    @Column(nullable = false)
    private Integer targetMonths;

    @NotNull(message = "Current savings are required")
    @PositiveOrZero(message = "Current savings cannot be negative")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal currentSavings;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
