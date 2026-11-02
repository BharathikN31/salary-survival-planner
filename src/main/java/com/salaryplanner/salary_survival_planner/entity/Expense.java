package com.salaryplanner.salary_survival_planner.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Expense title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotNull(message = "Expense amount is required")
    @Positive(message = "Expense amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Expense date is required")
    private LocalDate expenseDate;
    private LocalDateTime createdAt;
    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
