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

    @Autowired
    private CurrentUserService currentUserService;

    // Add savings goal
    public SavingsGoal addSavingsGoal(SavingsGoal newGoal) {

        Long userId = currentUserService.getCurrentUserId();

        newGoal.setUserId(userId);

        return savingsGoalRepository.save(newGoal);
    }

    // Get savings goal by ID
    public SavingsGoal getSavingsGoalById(Long id) {

        Long userId = currentUserService.getCurrentUserId();

        return savingsGoalRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Savings goal not found with ID: " + id
                ));
    }

    // Get all savings goals
    public List<SavingsGoal> getAllSavingsGoals() {

        Long userId = currentUserService.getCurrentUserId();

        return savingsGoalRepository.findByUserId(userId);
    }

    // Update savings goal
    public SavingsGoal updateSavingsGoal(Long id, SavingsGoal modifyGoal) {

        SavingsGoal existingGoal = getSavingsGoalById(id);

        existingGoal.setName(modifyGoal.getName());
        existingGoal.setTotalAmount(modifyGoal.getTotalAmount());
        existingGoal.setInvestedAmount(modifyGoal.getInvestedAmount());
        existingGoal.setTargetDate(modifyGoal.getTargetDate());

        return savingsGoalRepository.save(existingGoal);
    }

    // Delete savings goal
    public void deleteSavingsGoal(Long id) {

        SavingsGoal existingGoal = getSavingsGoalById(id);

        savingsGoalRepository.delete(existingGoal);
    }
}