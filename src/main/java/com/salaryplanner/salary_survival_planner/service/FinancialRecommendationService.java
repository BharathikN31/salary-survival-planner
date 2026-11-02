package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.dto.FinancialRecommendation;
import com.salaryplanner.salary_survival_planner.dto.FinancialSummary;
import com.salaryplanner.salary_survival_planner.dto.OverspendingAlert;
import com.salaryplanner.salary_survival_planner.dto.PaymentReminder;
import com.salaryplanner.salary_survival_planner.dto.FinancialHealthScore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class FinancialRecommendationService {

    @Autowired
    private FinancialSummaryService financialSummaryService;

    @Autowired
    private OverspendingAlertService overspendingAlertService;

    @Autowired
    private PaymentReminderService paymentReminderService;

    @Autowired
    private FinancialHealthScoreService financialHealthScoreService;


    public List<FinancialRecommendation> getRecommendations(Long userId) {

        List<FinancialRecommendation> recommendations =
                new ArrayList<>();

        FinancialSummary summary =
                financialSummaryService.getFinancialSummary(userId);

        List<OverspendingAlert> alerts =
                overspendingAlertService.getOverspendingAlerts(userId);

        List<PaymentReminder> reminders =
                paymentReminderService.getPaymentReminders(userId);

        FinancialHealthScore healthScore =
                financialHealthScoreService.calculateHealthScore(userId);


        // Recommendation 1: Review overspending

        if (!alerts.isEmpty()) {

            addRecommendation(
                    recommendations,
                    userId,
                    "EXPENSE MANAGEMENT",
                    "Review your spending in categories that have reached or exceeded their monthly budget.",
                    "HIGH"
            );
        }


        // Recommendation 2: Review upcoming or overdue payments

        if (!reminders.isEmpty()) {

            addRecommendation(
                    recommendations,
                    userId,
                    "PAYMENT MANAGEMENT",
                    "Review your upcoming and overdue payments and plan your available balance accordingly.",
                    "HIGH"
            );
        }


        // Recommendation 3: Review remaining balance

        BigDecimal remainingBalance = summary.getRemainingBalance();

        if (remainingBalance != null
                && remainingBalance.compareTo(BigDecimal.ZERO) < 0) {

            addRecommendation(
                    recommendations,
                    userId,
                    "CASH FLOW",
                    "Your calculated remaining balance is negative. Review your expenses and commitments before making additional discretionary purchases.",
                    "HIGH"
            );

        } else if (remainingBalance != null
                && remainingBalance.compareTo(BigDecimal.ZERO) == 0) {

            addRecommendation(
                    recommendations,
                    userId,
                    "CASH FLOW",
                    "Your calculated remaining balance is zero. Review upcoming expenses and avoid relying on funds that are not available.",
                    "MEDIUM"
            );
        }


        // Recommendation 4: Improve financial health

        if (healthScore.getHealthScore() != null
                && healthScore.getHealthScore() < 50) {

            addRecommendation(
                    recommendations,
                    userId,
                    "FINANCIAL HEALTH",
                    "Review your expense-to-income and commitment-to-income ratios. Start with essential expenses and obligations before adjusting discretionary spending.",
                    "HIGH"
            );
        }


        // Recommendation 5: Build savings

        if (remainingBalance != null
                && remainingBalance.compareTo(BigDecimal.ZERO) > 0) {

            addRecommendation(
                    recommendations,
                    userId,
                    "SAVINGS",
                    "Review your savings goals and emergency fund. Consider allocating a manageable portion of your remaining balance to savings after accounting for upcoming expenses.",
                    "MEDIUM"
            );
        }


        // Recommendation 6: General financial planning

        if (recommendations.isEmpty()) {

            addRecommendation(
                    recommendations,
                    userId,
                    "FINANCIAL PLANNING",
                    "Continue tracking your expenses, reviewing your budget, and updating your financial goals regularly.",
                    "LOW"
            );
        }

        return recommendations;
    }


    private void addRecommendation(
            List<FinancialRecommendation> recommendations,
            Long userId,
            String category,
            String recommendationText,
            String priority) {

        FinancialRecommendation recommendation =
                new FinancialRecommendation();

        recommendation.setUserId(userId);
        recommendation.setCategory(category);
        recommendation.setRecommendation(recommendationText);
        recommendation.setPriority(priority);

        recommendations.add(recommendation);
    }
}