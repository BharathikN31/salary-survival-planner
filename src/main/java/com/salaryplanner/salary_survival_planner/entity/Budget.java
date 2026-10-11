package com.salaryplanner.salary_survival_planner.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

import java.time.LocalDateTime;

@Data
@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @NotNull
    private Long userId;
    @NotNull
    private String name;
    @NotNull
    private String category;
    @NotNull
    private BigDecimal monthlyLimit;
    private LocalDateTime createdAt;

    @PrePersist
    public  void onCreate(){
        createdAt=LocalDateTime.now();
    }
}
