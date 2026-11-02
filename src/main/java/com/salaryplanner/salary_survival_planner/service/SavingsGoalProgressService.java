package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.dto.SavingsGoalProgress;
import com.salaryplanner.salary_survival_planner.entity.SavingsGoal;
import com.salaryplanner.salary_survival_planner.repository.SavingsGoalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class SavingsGoalProgressService {

    @Autowired
    private SavingsGoalRepository savingsGoalRepository;

    public List<SavingsGoalProgress> getSavingsGoalProgress(Long userId) {

        List<SavingsGoal> goals =
                savingsGoalRepository.findByUserId(userId);

        List<SavingsGoalProgress> progressList = new ArrayList<>();

        for (SavingsGoal goal : goals) {

            SavingsGoalProgress progress = new SavingsGoalProgress();

            BigDecimal totalAmount = goal.getTotalAmount();
            BigDecimal investedAmount = goal.getInvestedAmount();

            BigDecimal remainingAmount =
                    totalAmount.subtract(investedAmount);

            if (remainingAmount.compareTo(BigDecimal.ZERO) < 0) {
                remainingAmount = BigDecimal.ZERO;
            }

            BigDecimal completionPercentage;

            if (totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
                completionPercentage = BigDecimal.ZERO;
            } else {
                completionPercentage = investedAmount
                        .multiply(BigDecimal.valueOf(100))
                        .divide(totalAmount, 2, RoundingMode.HALF_UP);

                if (completionPercentage.compareTo(
                        BigDecimal.valueOf(100)) > 0) {
                    completionPercentage = BigDecimal.valueOf(100);
                }
            }

            String progressStatus;

            if (investedAmount.compareTo(totalAmount) >= 0) {
                progressStatus = "COMPLETED";
            } else {
                progressStatus = "IN PROGRESS";
            }

            progress.setUserId(userId);
            progress.setGoalId(goal.getId());
            progress.setGoalName(goal.getName());
            progress.setTotalAmount(totalAmount);
            progress.setInvestedAmount(investedAmount);
            progress.setRemainingAmount(remainingAmount);
            progress.setCompletionPercentage(completionPercentage);
            progress.setProgressStatus(progressStatus);

            progressList.add(progress);
        }

        return progressList;
    }
}