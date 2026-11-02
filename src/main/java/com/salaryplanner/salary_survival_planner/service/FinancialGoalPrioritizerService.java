package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.dto.FinancialGoalPriority;
import com.salaryplanner.salary_survival_planner.entity.SavingsGoal;
import com.salaryplanner.salary_survival_planner.repository.SavingsGoalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class FinancialGoalPrioritizerService {

    @Autowired
    private SavingsGoalRepository savingsGoalRepository;

    public List<FinancialGoalPriority> prioritizeGoals(Long userId) {

        List<SavingsGoal> goals =
                savingsGoalRepository.findByUserId(userId);

        List<FinancialGoalPriority> priorities = new ArrayList<>();

        LocalDate today = LocalDate.now();

        for (SavingsGoal goal : goals) {

            BigDecimal targetAmount = goal.getTotalAmount();
            BigDecimal investedAmount = goal.getInvestedAmount();

            BigDecimal remainingAmount =
                    targetAmount.subtract(investedAmount);

            if (remainingAmount.compareTo(BigDecimal.ZERO) < 0) {
                remainingAmount = BigDecimal.ZERO;
            }

            long daysRemaining =
                    ChronoUnit.DAYS.between(today, goal.getTargetDate());

            long monthsRemaining = Math.max(
                    1, (long) Math.ceil(Math.max(1, daysRemaining) / 30.0));

            BigDecimal monthlyContribution = BigDecimal.ZERO;

            if (remainingAmount.compareTo(BigDecimal.ZERO) > 0) {
                monthlyContribution = remainingAmount.divide(
                        BigDecimal.valueOf(monthsRemaining),
                        2,
                        RoundingMode.UP);
            }

            int priorityScore = calculatePriorityScore(
                    remainingAmount, targetAmount, daysRemaining);

            String priorityLevel;

            if (remainingAmount.compareTo(BigDecimal.ZERO) == 0) {
                priorityLevel = "COMPLETED";
            } else if (priorityScore >= 70) {
                priorityLevel = "HIGH";
            } else if (priorityScore >= 40) {
                priorityLevel = "MEDIUM";
            } else {
                priorityLevel = "LOW";
            }

            FinancialGoalPriority priority =
                    new FinancialGoalPriority();

            priority.setUserId(userId);
            priority.setGoalId(goal.getId());
            priority.setGoalName(goal.getName());
            priority.setTargetAmount(targetAmount);
            priority.setInvestedAmount(investedAmount);
            priority.setRemainingAmount(remainingAmount);
            priority.setTargetDate(goal.getTargetDate());
            priority.setDaysRemaining(Math.max(0, daysRemaining));
            priority.setRequiredMonthlyContribution(monthlyContribution);
            priority.setPriorityScore(priorityScore);
            priority.setPriorityLevel(priorityLevel);

            priorities.add(priority);
        }

        priorities.sort(
                Comparator.comparingInt(
                        FinancialGoalPriority::getPriorityScore
                ).reversed()
        );

        return priorities;
    }

    private int calculatePriorityScore(
            BigDecimal remainingAmount,
            BigDecimal targetAmount,
            long daysRemaining) {

        if (remainingAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return 0;
        }

        int urgencyScore;

        if (daysRemaining <= 0) {
            urgencyScore = 50;
        } else if (daysRemaining <= 30) {
            urgencyScore = 45;
        } else if (daysRemaining <= 90) {
            urgencyScore = 35;
        } else if (daysRemaining <= 180) {
            urgencyScore = 25;
        } else {
            urgencyScore = 15;
        }

        BigDecimal remainingPercentage = remainingAmount
                .multiply(BigDecimal.valueOf(100))
                .divide(targetAmount, 2, RoundingMode.HALF_UP);

        int remainingScore = remainingPercentage
                .multiply(BigDecimal.valueOf(0.5))
                .intValue();

        return Math.min(100, urgencyScore + remainingScore);
    }
}