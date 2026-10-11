package com.salaryplanner.salary_survival_planner.repository;
import com.salaryplanner.salary_survival_planner.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    List<Expense> findByUserId(Long userId);
}
