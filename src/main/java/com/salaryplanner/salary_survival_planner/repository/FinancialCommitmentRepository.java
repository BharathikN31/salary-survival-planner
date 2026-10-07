package com.salaryplanner.salary_survival_planner.repository;

import com.salaryplanner.salary_survival_planner.entity.FinancialCommitment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialCommitmentRepository extends JpaRepository<FinancialCommitment, Long> {
}
