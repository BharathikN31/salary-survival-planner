package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.entity.Expense;
import com.salaryplanner.salary_survival_planner.exception.ResourceNotFoundException;
import com.salaryplanner.salary_survival_planner.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CurrentUserService currentUserService;

    // Add expense
    public Expense addExpense(Expense newExpense) {

        Long userId = currentUserService.getCurrentUserId();

        newExpense.setUserId(userId);

        return expenseRepository.save(newExpense);
    }

    // Get expense by ID
    public Expense getExpenseById(Long id) {

        Long userId = currentUserService.getCurrentUserId();

        return expenseRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Expense not found with ID: " + id
                ));
    }

    // Get all expenses
    public List<Expense> getAllExpenses() {

        Long userId = currentUserService.getCurrentUserId();

        return expenseRepository.findByUserId(userId);
    }

    // Update expense
    public Expense updateExpense(Long id, Expense modifyExpense) {

        Expense existingExpense = getExpenseById(id);

        existingExpense.setTitle(modifyExpense.getTitle());
        existingExpense.setAmount(modifyExpense.getAmount());
        existingExpense.setCategory(modifyExpense.getCategory());
        existingExpense.setExpenseDate(modifyExpense.getExpenseDate());

        return expenseRepository.save(existingExpense);
    }

    // Delete expense
    public void deleteExpense(Long id) {

        Expense existingExpense = getExpenseById(id);

        expenseRepository.delete(existingExpense);
    }
}