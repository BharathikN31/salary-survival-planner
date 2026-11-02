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

    public Budget addBudget(Budget newBudget) {
        return budgetRepository.save(newBudget);
    }

    public Budget getBudgetById(Long id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Budget not found with ID: " + id
                ));
    }

    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    public Budget updateBudget(Long id, Budget modifyBudget) {

        Budget existingBudget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Budget not found with ID: " + id
                ));

        existingBudget.setName(modifyBudget.getName());
        existingBudget.setCategory(modifyBudget.getCategory());
        existingBudget.setMonthlyLimit(modifyBudget.getMonthlyLimit());

        return budgetRepository.save(existingBudget);
    }

    public void deleteBudget(Long id) {

        Budget existingBudget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Budget not found with ID: " + id
                ));

        budgetRepository.delete(existingBudget);
    }
}