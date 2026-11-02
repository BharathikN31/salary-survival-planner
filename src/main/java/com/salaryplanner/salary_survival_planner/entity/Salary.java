package com.salaryplanner.salary_survival_planner.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Salary amount is required")
    @Positive(message = "Salary amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Salary date is required")
    private LocalDate salaryDate;
    private LocalDateTime createdAt;
    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
