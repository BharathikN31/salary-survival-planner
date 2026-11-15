
package com.salaryplanner.salary_survival_planner.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_budget_user_category",
                        columnNames = {"user_id", "category"}
                )
        }
)
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotBlank(message = "Budget name is required")
    @Size(max = 100, message = "Budget name cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category cannot exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String category;

    @NotNull(message = "Monthly limit is required")
    @Positive(message = "Monthly limit must be greater than zero")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal monthlyLimit;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
