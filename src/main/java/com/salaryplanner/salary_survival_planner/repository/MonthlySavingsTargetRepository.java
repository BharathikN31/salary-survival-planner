package com.salaryplanner.salary_survival_planner.repository;

import com.salaryplanner.salary_survival_planner.entity.MonthlySavingsTarget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonthlySavingsTargetRepository
        extends JpaRepository<MonthlySavingsTarget, Long> {

    Optional<MonthlySavingsTarget>
    findByUserIdAndTargetYearAndTargetMonth(
            Long userId,
            Integer targetYear,
            Integer targetMonth
    );
}