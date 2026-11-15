
package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.entity.Budget;
import com.salaryplanner.salary_survival_planner.exception.ResourceNotFoundException;
import com.salaryplanner.salary_survival_planner.repository.BudgetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private CurrentUserService currentUserService;

    // Normalize category to avoid FOOD and food being treated differently
    private String normalizeCategory(String category) {
        return category.trim().toUpperCase(Locale.ROOT);
    }

    // Add budget
    public Budget addBudget(Budget newBudget) {

        Long userId = currentUserService.getCurrentUserId();

        newBudget.setId(null);
        newBudget.setUserId(userId);

        String category = normalizeCategory(newBudget.getCategory());

        if (budgetRepository.existsByUserIdAndCategory(userId, category)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A budget already exists for this category"
            );
        }

        newBudget.setCategory(category);

        try {
            return budgetRepository.saveAndFlush(newBudget);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A budget already exists for this category"
            );
        }
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

        Long userId = currentUserService.getCurrentUserId();

        String category = normalizeCategory(modifyBudget.getCategory());

        boolean duplicate = budgetRepository
                .existsByUserIdAndCategoryAndIdNot(
                        userId, category, id
                );

        if (duplicate) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A budget already exists for this category"
            );
        }

        existingBudget.setName(modifyBudget.getName());
        existingBudget.setCategory(category);
        existingBudget.setMonthlyLimit(modifyBudget.getMonthlyLimit());

        try {
            return budgetRepository.saveAndFlush(existingBudget);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A budget already exists for this category"
            );
        }
    }

    // Delete budget
    public void deleteBudget(Long id) {

        Budget existingBudget = getBudgetById(id);

        budgetRepository.delete(existingBudget);
    }
}
