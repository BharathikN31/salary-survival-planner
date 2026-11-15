
package com.salaryplanner.salary_survival_planner.entity;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bank_accounts")
@Data
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Assigned automatically from authenticated JWT
    private Long userId;

    @NotBlank(message = "Account name is required")
    @Size(max = 100, message = "Account name cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String accountName;

    @NotNull(message = "Current balance is required")
    @PositiveOrZero(message = "Bank balance cannot be negative")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal currentBalance;

    private LocalDateTime lastUpdated;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        lastUpdated = LocalDateTime.now();
    }
}
