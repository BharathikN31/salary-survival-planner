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

    public Expense addExpense(Expense newExpense) {
        return expenseRepository.save(newExpense);
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Expense not found with ID: " + id
                ));
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense updateExpense(Long id, Expense modifyExpense) {

        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Expense not found with ID: " + id
                ));

        existingExpense.setTitle(modifyExpense.getTitle());
        existingExpense.setAmount(modifyExpense.getAmount());
        existingExpense.setCategory(modifyExpense.getCategory());
        existingExpense.setExpenseDate(modifyExpense.getExpenseDate());

        return expenseRepository.save(existingExpense);
    }

    public void deleteExpense(Long id) {

        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Expense not found with ID: " + id
                ));

        expenseRepository.delete(existingExpense);
    }
}