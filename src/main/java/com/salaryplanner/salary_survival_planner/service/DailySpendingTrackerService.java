
package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.dto.DailySpendingSummary;
import com.salaryplanner.salary_survival_planner.entity.Expense;
import com.salaryplanner.salary_survival_planner.repository.ExpenseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DailySpendingTrackerService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private FinancialSummaryService financialSummaryService;

    @Autowired
    private CurrentUserService currentUserService;

    public DailySpendingSummary getDailySpendingSummary(
            LocalDate spendingDate) {

        // Get authenticated user's ID from JWT
        Long userId = currentUserService.getCurrentUserId();

        // Fetch only this user's expenses
        List<Expense> expenses =
                expenseRepository.findByUserId(userId);

        BigDecimal totalSpent = BigDecimal.ZERO;

        // Calculate expenses for the requested date
        for (Expense expense : expenses) {
            if (spendingDate.equals(expense.getExpenseDate())) {
                totalSpent = totalSpent.add(expense.getAmount());
            }
        }

        // Get the authenticated user's safe daily limit
        BigDecimal safeDailyLimit =
                financialSummaryService.getFinancialSummary()
                        .getSafeDailyLimit();

        if (safeDailyLimit == null) {
            safeDailyLimit = BigDecimal.ZERO;
        }

        BigDecimal remainingLimit =
                safeDailyLimit.subtract(totalSpent);

        BigDecimal exceededAmount = BigDecimal.ZERO;

        String spendingStatus;

        if (remainingLimit.compareTo(BigDecimal.ZERO) < 0) {

            exceededAmount = remainingLimit.abs();
            remainingLimit = BigDecimal.ZERO;
            spendingStatus = "LIMIT EXCEEDED";

        } else if (totalSpent.compareTo(safeDailyLimit) == 0) {

            spendingStatus = "LIMIT REACHED";

        } else {

            spendingStatus = "WITHIN LIMIT";
        }

        DailySpendingSummary summary = new DailySpendingSummary();

        summary.setUserId(userId);
        summary.setSpendingDate(spendingDate);
        summary.setSafeDailyLimit(safeDailyLimit);
        summary.setTotalSpent(totalSpent);
        summary.setRemainingLimit(remainingLimit);
        summary.setExceededAmount(exceededAmount);
        summary.setSpendingStatus(spendingStatus);

        return summary;
    }
}
