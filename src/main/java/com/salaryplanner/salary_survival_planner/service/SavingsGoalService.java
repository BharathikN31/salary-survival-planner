package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.entity.SavingsGoal;
import com.salaryplanner.salary_survival_planner.exception.ResourceNotFoundException;
import com.salaryplanner.salary_survival_planner.repository.SavingsGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavingsGoalService {

    @Autowired
    private SavingsGoalRepository savingsGoalRepository;

    public SavingsGoal addSavingsGoal(SavingsGoal newGoal) {
        return savingsGoalRepository.save(newGoal);
    }

    public SavingsGoal getSavingsGoalById(Long id) {
        return savingsGoalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Savings goal not found with ID: " + id
                ));
    }

    public List<SavingsGoal> getAllSavingsGoals() {
        return savingsGoalRepository.findAll();
    }

    public SavingsGoal updateSavingsGoal(Long id, SavingsGoal modifyGoal) {

        SavingsGoal existingGoal = savingsGoalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Savings goal not found with ID: " + id
                ));

        existingGoal.setName(modifyGoal.getName());
        existingGoal.setTotalAmount(modifyGoal.getTotalAmount());
        existingGoal.setInvestedAmount(modifyGoal.getInvestedAmount());
        existingGoal.setTargetDate(modifyGoal.getTargetDate());

        return savingsGoalRepository.save(existingGoal);
    }

    public void deleteSavingsGoal(Long id) {

        SavingsGoal existingGoal = savingsGoalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Savings goal not found with ID: " + id
                ));

        savingsGoalRepository.delete(existingGoal);
    }
}