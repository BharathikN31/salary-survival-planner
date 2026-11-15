
package com.salaryplanner.salary_survival_planner.repository;

import com.salaryplanner.salary_survival_planner.entity.InvestmentTransaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InvestmentTransactionRepository
        extends JpaRepository<InvestmentTransaction, Long> {

    List<InvestmentTransaction> findByUserId(Long userId);

    List<InvestmentTransaction> findBySavingsGoalId(Long savingsGoalId);

    List<InvestmentTransaction>
    findByUserIdAndTransactionDateBetween(
            Long userId,
            LocalDate startDate,
            LocalDate endDate);

    Optional<InvestmentTransaction> findByIdAndUserId(
            Long id, Long userId);

    List<InvestmentTransaction> findBySavingsGoalIdAndUserId(
            Long savingsGoalId, Long userId);
}
