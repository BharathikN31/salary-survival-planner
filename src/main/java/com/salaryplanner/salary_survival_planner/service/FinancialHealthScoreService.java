package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.dto.FinancialHealthScore;
import com.salaryplanner.salary_survival_planner.entity.*;
import com.salaryplanner.salary_survival_planner.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FinancialHealthScoreService {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private FinancialCommitmentRepository commitmentRepository;

    public FinancialHealthScore calculateHealthScore(Long userId) {

        LocalDate today = LocalDate.now();

        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        BigDecimal income = salaryRepository.findByUserId(userId)
                .stream()
                .filter(s -> s.getSalaryDate() != null
                        && s.getSalaryDate().getMonthValue() == currentMonth
                        && s.getSalaryDate().getYear() == currentYear)
                .map(s -> s.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expenses = expenseRepository.findByUserId(userId)
                .stream()
                .filter(e -> e.getExpenseDate() != null
                        && e.getExpenseDate().getMonthValue() == currentMonth
                        && e.getExpenseDate().getYear() == currentYear)
                .map(e -> e.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal commitments =
                commitmentRepository.findByUserId(userId)
                        .stream()
                        .filter(c -> c.getDueDate() != null
                                && c.getDueDate().getMonthValue() == currentMonth
                                && c.getDueDate().getYear() == currentYear)
                        .map(c -> c.getAmount())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal remainingBalance =
                income.subtract(expenses).subtract(commitments);

        BigDecimal expenseRatio = BigDecimal.ZERO;
        BigDecimal commitmentRatio = BigDecimal.ZERO;
        BigDecimal savingsRatio = BigDecimal.ZERO;

        int score = 0;

        if (income.compareTo(BigDecimal.ZERO) > 0) {

            expenseRatio = expenses.multiply(BigDecimal.valueOf(100))
                    .divide(income, 2, RoundingMode.HALF_UP);

            commitmentRatio = commitments.multiply(BigDecimal.valueOf(100))
                    .divide(income, 2, RoundingMode.HALF_UP);

            savingsRatio = remainingBalance.multiply(BigDecimal.valueOf(100))
                    .divide(income, 2, RoundingMode.HALF_UP);

            // Expense score: maximum 40 points
            BigDecimal expenseScore = BigDecimal.valueOf(40)
                    .subtract(expenseRatio.multiply(BigDecimal.valueOf(0.4)));

            // Commitment score: maximum 30 points
            BigDecimal commitmentScore = BigDecimal.valueOf(30)
                    .subtract(commitmentRatio.multiply(BigDecimal.valueOf(0.3)));

            // Savings score: maximum 30 points
            BigDecimal savingsScore = savingsRatio
                    .multiply(BigDecimal.valueOf(1.5));

            if (expenseScore.compareTo(BigDecimal.ZERO) < 0) {
                expenseScore = BigDecimal.ZERO;
            }

            if (commitmentScore.compareTo(BigDecimal.ZERO) < 0) {
                commitmentScore = BigDecimal.ZERO;
            }

            if (savingsScore.compareTo(BigDecimal.valueOf(30)) > 0) {
                savingsScore = BigDecimal.valueOf(30);
            }

            if (savingsScore.compareTo(BigDecimal.ZERO) < 0) {
                savingsScore = BigDecimal.ZERO;
            }

            score = expenseScore.add(commitmentScore)
                    .add(savingsScore)
                    .setScale(0, RoundingMode.HALF_UP)
                    .intValue();

            score = Math.min(100, Math.max(0, score));
        }

        String category;

        if (income.compareTo(BigDecimal.ZERO) <= 0) {
            category = "NO INCOME DATA";
        } else if (score >= 80) {
            category = "EXCELLENT";
        } else if (score >= 60) {
            category = "GOOD";
        } else if (score >= 40) {
            category = "FAIR";
        } else {
            category = "POOR";
        }

        List<String> suggestions = generateSuggestions(
                income, expenseRatio, commitmentRatio, savingsRatio);

        FinancialHealthScore result = new FinancialHealthScore();

        result.setUserId(userId);
        result.setTotalIncome(income);
        result.setTotalExpenses(expenses);
        result.setTotalCommitments(commitments);
        result.setRemainingBalance(remainingBalance);
        result.setExpenseRatio(expenseRatio);
        result.setCommitmentRatio(commitmentRatio);
        result.setSavingsRatio(savingsRatio);
        result.setHealthScore(score);
        result.setHealthCategory(category);
        result.setSuggestions(suggestions);

        return result;
    }

    private List<String> generateSuggestions(
            BigDecimal income,
            BigDecimal expenseRatio,
            BigDecimal commitmentRatio,
            BigDecimal savingsRatio) {

        List<String> suggestions = new ArrayList<>();

        if (income.compareTo(BigDecimal.ZERO) <= 0) {
            suggestions.add("Add salary information for this month to calculate your financial health.");
            return suggestions;
        }

        if (expenseRatio.compareTo(BigDecimal.valueOf(50)) > 0) {
            suggestions.add("Your expenses are high. Try reducing unnecessary spending.");
        }

        if (commitmentRatio.compareTo(BigDecimal.valueOf(40)) > 0) {
            suggestions.add("Your financial commitments are high compared to your income.");
        }

        if (savingsRatio.compareTo(BigDecimal.valueOf(20)) < 0) {
            suggestions.add("Try to increase your monthly savings.");
        }

        if (suggestions.isEmpty()) {
            suggestions.add("Good financial progress! Continue maintaining your spending and savings habits.");
        }

        return suggestions;
    }
}