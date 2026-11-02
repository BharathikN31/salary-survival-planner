package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.dto.MonthlyExpenseInsight;
import com.salaryplanner.salary_survival_planner.entity.Expense;
import com.salaryplanner.salary_survival_planner.repository.ExpenseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MonthlyExpenseInsightService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<MonthlyExpenseInsight> getMonthlyExpenseInsights(
            Long userId, int month, int year) {

        List<Expense> expenses =
                expenseRepository.findByUserId(userId);

        Map<String, BigDecimal> categoryTotals = new HashMap<>();

        BigDecimal totalSpent = BigDecimal.ZERO;

        for (Expense expense : expenses) {

            if (expense.getExpenseDate().getMonthValue() == month
                    && expense.getExpenseDate().getYear() == year) {

                String category = expense.getCategory();

                categoryTotals.put(
                        category,
                        categoryTotals.getOrDefault(
                                category, BigDecimal.ZERO
                        ).add(expense.getAmount())
                );

                totalSpent = totalSpent.add(expense.getAmount());
            }
        }

        List<MonthlyExpenseInsight> insights = new ArrayList<>();

        for (Map.Entry<String, BigDecimal> entry
                : categoryTotals.entrySet()) {

            MonthlyExpenseInsight insight =
                    new MonthlyExpenseInsight();

            BigDecimal categoryTotal = entry.getValue();

            BigDecimal percentage = BigDecimal.ZERO;

            if (totalSpent.compareTo(BigDecimal.ZERO) > 0) {
                percentage = categoryTotal
                        .multiply(BigDecimal.valueOf(100))
                        .divide(totalSpent, 2, RoundingMode.HALF_UP);
            }

            insight.setUserId(userId);
            insight.setCategory(entry.getKey());
            insight.setTotalSpent(categoryTotal);
            insight.setPercentageOfTotal(percentage);

            insights.add(insight);
        }

        return insights;
    }
}