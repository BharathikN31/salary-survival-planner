package com.salaryplanner.salary_survival_planner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private Long userId;

    private String name;

    private String email;

    private String token;

    private String tokenType;
}