package com.salaryplanner.salary_survival_planner.repository;

import com.salaryplanner.salary_survival_planner.entity.FinancialCommitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FinancialCommitmentRepository
        extends JpaRepository<FinancialCommitment, Long> {

    List<FinancialCommitment> findByUserId(Long userId);

    Optional<FinancialCommitment> findByIdAndUserId(Long id, Long userId);
}