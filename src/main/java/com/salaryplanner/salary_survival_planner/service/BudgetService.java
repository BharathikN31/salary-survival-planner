package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.entity.Budget;
import com.salaryplanner.salary_survival_planner.exception.ResourceNotFoundException;
import com.salaryplanner.salary_survival_planner.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private CurrentUserService currentUserService;

    // Add budget
    public Budget addBudget(Budget newBudget) {

        Long userId = currentUserService.getCurrentUserId();

        newBudget.setUserId(userId);

        return budgetRepository.save(newBudget);
    }

    // Get budget by ID
    public Budget getBudgetById(Long id) {

        Long userId = currentUserService.getCurrentUserId();

        return budgetRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Budget not found with ID: " + id
                ));
    }

    // Get all budgets
    public List<Budget> getAllBudgets() {

        Long userId = currentUserService.getCurrentUserId();

        return budgetRepository.findByUserId(userId);
    }

    // Update budget
    public Budget updateBudget(Long id, Budget modifyBudget) {

        Budget existingBudget = getBudgetById(id);

        existingBudget.setName(modifyBudget.getName());
        existingBudget.setCategory(modifyBudget.getCategory());
        existingBudget.setMonthlyLimit(modifyBudget.getMonthlyLimit());

        return budgetRepository.save(existingBudget);
    }

    // Delete budget
    public void deleteBudget(Long id) {

        Budget existingBudget = getBudgetById(id);

        budgetRepository.delete(existingBudget);
    }
}