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

    public DailySpendingSummary getDailySpendingSummary(
            Long userId, LocalDate spendingDate) {

        List<Expense> expenses =
                expenseRepository.findByUserId(userId);

        BigDecimal totalSpent = BigDecimal.ZERO;

        for (Expense expense : expenses) {
            if (spendingDate.equals(expense.getExpenseDate())) {
                totalSpent = totalSpent.add(expense.getAmount());
            }
        }

        BigDecimal safeDailyLimit =
                financialSummaryService.getFinancialSummary(userId)
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