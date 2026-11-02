package com.salaryplanner.salary_survival_planner.repository;

import com.salaryplanner.salary_survival_planner.entity.EmergencyFund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmergencyFundRepository
        extends JpaRepository<EmergencyFund, Long> {

    Optional<EmergencyFund> findByUserId(Long userId);
}