package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.dto.EmergencyFundPlan;
import com.salaryplanner.salary_survival_planner.entity.EmergencyFund;
import com.salaryplanner.salary_survival_planner.exception.ResourceNotFoundException;
import com.salaryplanner.salary_survival_planner.repository.EmergencyFundRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class EmergencyFundService {

    @Autowired
    private EmergencyFundRepository emergencyFundRepository;

    public EmergencyFundPlan calculateEmergencyFund(Long userId) {

        EmergencyFund fund = emergencyFundRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Emergency fund details not found for user ID: "
                                        + userId
                        ));

        BigDecimal monthlyExpenses = fund.getMonthlyEssentialExpenses();
        BigDecimal currentSavings = fund.getCurrentSavings();
        Integer targetMonths = fund.getTargetMonths();

        BigDecimal recommendedFund = monthlyExpenses.multiply(
                BigDecimal.valueOf(targetMonths));

        BigDecimal remainingAmount = recommendedFund.subtract(currentSavings);

        if (remainingAmount.compareTo(BigDecimal.ZERO) < 0) {
            remainingAmount = BigDecimal.ZERO;
        }

        BigDecimal completionPercentage = BigDecimal.ZERO;

        if (recommendedFund.compareTo(BigDecimal.ZERO) > 0) {
            completionPercentage = currentSavings
                    .multiply(BigDecimal.valueOf(100))
                    .divide(recommendedFund, 2, RoundingMode.HALF_UP);

            if (completionPercentage.compareTo(BigDecimal.valueOf(100)) > 0) {
                completionPercentage = BigDecimal.valueOf(100);
            }
        }

        String fundStatus;

        if (currentSavings.compareTo(recommendedFund) >= 0) {
            fundStatus = "TARGET ACHIEVED";
        } else {
            fundStatus = "SAVING IN PROGRESS";
        }

        EmergencyFundPlan plan = new EmergencyFundPlan();

        plan.setUserId(userId);
        plan.setMonthlyEssentialExpenses(monthlyExpenses);
        plan.setTargetMonths(targetMonths);
        plan.setRecommendedFund(recommendedFund);
        plan.setCurrentSavings(currentSavings);
        plan.setRemainingAmount(remainingAmount);
        plan.setCompletionPercentage(completionPercentage);
        plan.setFundStatus(fundStatus);

        return plan;
    }

    public EmergencyFund saveEmergencyFund(EmergencyFund fund) {
        return emergencyFundRepository.save(fund);
    }
}