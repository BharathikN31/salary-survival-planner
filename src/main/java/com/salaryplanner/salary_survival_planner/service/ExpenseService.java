package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.entity.Expense;
import com.salaryplanner.salary_survival_planner.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    private  ExpenseRepository expenseRepository;

    public Expense addExpense(Expense newExpense){
        return expenseRepository.save(newExpense);
    }
    public Expense getExpenseById(Long id){
        return  expenseRepository.findById(id).orElse(null);
    }
    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }

    public Expense updateExpense(Long id,Expense modifyExpense){
        Expense updateExpense=expenseRepository.findById(id).orElse(null);
        if(updateExpense!=null){
            updateExpense.setTitle(modifyExpense.getTitle());
            updateExpense.setAmount(modifyExpense.getAmount());
            updateExpense.setCategory(modifyExpense.getCategory());
            updateExpense.setExpenseDate(modifyExpense.getExpenseDate());

            return expenseRepository.save(updateExpense);
        }
        return null;
    }

    public void deleteExpense(Long id){
        expenseRepository.deleteById(id);
    }
}
