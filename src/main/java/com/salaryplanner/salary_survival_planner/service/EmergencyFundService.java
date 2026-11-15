
package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.dto.EmergencyFundPlan;
import com.salaryplanner.salary_survival_planner.dto.EmergencyFundRequest;
import com.salaryplanner.salary_survival_planner.entity.EmergencyFund;
import com.salaryplanner.salary_survival_planner.exception.ResourceNotFoundException;
import com.salaryplanner.salary_survival_planner.repository.EmergencyFundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class EmergencyFundService {

    @Autowired
    private EmergencyFundRepository emergencyFundRepository;

    @Autowired
    private CurrentUserService currentUserService;

    @Transactional
    public EmergencyFund saveEmergencyFund(EmergencyFundRequest request) {

        Long userId = currentUserService.getCurrentUserId();

        EmergencyFund fund = emergencyFundRepository.findByUserId(userId)
                .orElseGet(EmergencyFund::new);

        fund.setUserId(userId);
        fund.setMonthlyEssentialExpenses(
                request.getMonthlyEssentialExpenses());
        fund.setTargetMonths(request.getTargetMonths());
        fund.setCurrentSavings(request.getCurrentSavings());

        return emergencyFundRepository.save(fund);
    }

    public EmergencyFundPlan calculateEmergencyFund() {

        Long userId = currentUserService.getCurrentUserId();

        EmergencyFund fund = emergencyFundRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Emergency fund details not found"
                        ));

        BigDecimal monthlyExpenses =
                fund.getMonthlyEssentialExpenses();

        BigDecimal currentSavings =
                fund.getCurrentSavings();

        Integer targetMonths = fund.getTargetMonths();

        BigDecimal recommendedFund = monthlyExpenses.multiply(
                BigDecimal.valueOf(targetMonths));

        BigDecimal remainingAmount =
                recommendedFund.subtract(currentSavings);

        if (remainingAmount.compareTo(BigDecimal.ZERO) < 0) {
            remainingAmount = BigDecimal.ZERO;
        }

        BigDecimal completionPercentage = BigDecimal.ZERO;

        if (recommendedFund.compareTo(BigDecimal.ZERO) > 0) {

            completionPercentage = currentSavings
                    .multiply(BigDecimal.valueOf(100))
                    .divide(recommendedFund, 2, RoundingMode.HALF_UP);

            if (completionPercentage.compareTo(
                    BigDecimal.valueOf(100)) > 0) {

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
}
