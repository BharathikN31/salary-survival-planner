
package com.salaryplanner.salary_survival_planner.repository;

import com.salaryplanner.salary_survival_planner.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByUserId(Long userId);

    Optional<Budget> findByIdAndUserId(Long id, Long userId);

    boolean existsByUserIdAndCategory(Long userId, String category);

    boolean existsByUserIdAndCategoryAndIdNot(
            Long userId, String category, Long id
    );
}
