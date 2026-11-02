package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.dto.SavingsContributionPlan;
import com.salaryplanner.salary_survival_planner.entity.SavingsGoal;
import com.salaryplanner.salary_survival_planner.repository.SavingsGoalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class SavingsContributionPlannerService {

    @Autowired
    private SavingsGoalRepository savingsGoalRepository;

    public List<SavingsContributionPlan> getContributionPlans(Long userId) {

        List<SavingsGoal> goals =
                savingsGoalRepository.findByUserId(userId);

        List<SavingsContributionPlan> plans = new ArrayList<>();

        LocalDate today = LocalDate.now();

        for (SavingsGoal goal : goals) {

            BigDecimal totalAmount = goal.getTotalAmount();
            BigDecimal investedAmount = goal.getInvestedAmount();

            BigDecimal remainingAmount =
                    totalAmount.subtract(investedAmount);

            if (remainingAmount.compareTo(BigDecimal.ZERO) < 0) {
                remainingAmount = BigDecimal.ZERO;
            }

            long daysRemaining = ChronoUnit.DAYS.between(
                    today, goal.getTargetDate());

            long monthsRemaining = Math.max(
                    1, (long) Math.ceil(daysRemaining / 30.0));

            BigDecimal dailyContribution = BigDecimal.ZERO;
            BigDecimal monthlyContribution = BigDecimal.ZERO;

            String planStatus;

            if (remainingAmount.compareTo(BigDecimal.ZERO) == 0) {

                planStatus = "GOAL ACHIEVED";

            } else if (daysRemaining <= 0) {

                planStatus = "TARGET DATE PASSED";

            } else {

                dailyContribution = remainingAmount.divide(
                        BigDecimal.valueOf(daysRemaining),
                        2,
                        RoundingMode.UP);

                monthlyContribution = remainingAmount.divide(
                        BigDecimal.valueOf(monthsRemaining),
                        2,
                        RoundingMode.UP);

                planStatus = "CONTRIBUTION REQUIRED";
            }

            SavingsContributionPlan plan =
                    new SavingsContributionPlan();

            plan.setUserId(userId);
            plan.setGoalId(goal.getId());
            plan.setGoalName(goal.getName());
            plan.setTargetAmount(totalAmount);
            plan.setInvestedAmount(investedAmount);
            plan.setRemainingAmount(remainingAmount);
            plan.setTargetDate(goal.getTargetDate());
            plan.setDaysRemaining(Math.max(0, daysRemaining));
            plan.setMonthsRemaining(monthsRemaining);
            plan.setRequiredDailyContribution(dailyContribution);
            plan.setRequiredMonthlyContribution(monthlyContribution);
            plan.setPlanStatus(planStatus);

            plans.add(plan);
        }

        return plans;
    }
}