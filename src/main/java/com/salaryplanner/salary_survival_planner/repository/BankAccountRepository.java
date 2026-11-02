package com.salaryplanner.salary_survival_planner.repository;

import com.salaryplanner.salary_survival_planner.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository
        extends JpaRepository<BankAccount, Long> {

    List<BankAccount> findByUserId(Long userId);
}