package com.salaryplanner.salary_survival_planner.service;
import com.salaryplanner.salary_survival_planner.entity.Budget;
import com.salaryplanner.salary_survival_planner.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

    public Budget addBudget(Budget newBudget){
        return budgetRepository.save(newBudget);
    }
    public Budget getBudgetById(Long id){
        return  budgetRepository.findById(id).orElse(null);
    }
    public List<Budget> getAllBudgets(){
        return budgetRepository.findAll();
    }

    public Budget updateBudget(Long id,Budget modifyBudget){
        Budget updateBudget=budgetRepository.findById(id).orElse(null);
        if(updateBudget!=null){
            updateBudget.setName(modifyBudget.getName());
            updateBudget.setCategory(modifyBudget.getCategory());
            updateBudget.setMonthlyLimit(modifyBudget.getMonthlyLimit());

            return budgetRepository.save(updateBudget);
        }
        return null;
    }

    public void deleteBudget(Long id){
        budgetRepository.deleteById(id);
    }
}
