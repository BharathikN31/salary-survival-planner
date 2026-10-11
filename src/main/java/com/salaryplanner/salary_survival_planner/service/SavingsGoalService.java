package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.entity.SavingsGoal;
import com.salaryplanner.salary_survival_planner.repository.SavingsGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavingsGoalService {

    @Autowired
    private SavingsGoalRepository savingsGoalRepository;

    public SavingsGoal addSavingsGoal(SavingsGoal newGoal){
      return   savingsGoalRepository.save(newGoal);
    }

    public SavingsGoal getSavingsGoalById(Long id){
        return  savingsGoalRepository.findById(id).orElse(null);
    }

    public List<SavingsGoal> getAllSavingsGoals(){
        return  savingsGoalRepository.findAll();
    }

    public SavingsGoal updateSavingsGoal(Long id, SavingsGoal modifyGoal){
        SavingsGoal existingGoal=savingsGoalRepository.findById(id).orElse(null);
        if(existingGoal!=null) {
            existingGoal.setName(modifyGoal.getName());
            existingGoal.setTargetAmount(modifyGoal.getTargetAmount());
            existingGoal.setInvestedAmount(modifyGoal.getInvestedAmount());
            existingGoal.setTargetDate(modifyGoal.getTargetDate());
            return savingsGoalRepository.save(existingGoal);
        }
        return null;
    }

    public void deleteSavingsGoal(Long id){
        savingsGoalRepository.deleteById(id);
    }
}
