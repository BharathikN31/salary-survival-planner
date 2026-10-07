package com.salaryplanner.salary_survival_planner.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class FinancialCommitment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private String name;
    @NotNull
    private BigDecimal amount;
    private LocalDate dueDate;
    private Boolean recurring;
    private LocalDateTime createdAt;
}
