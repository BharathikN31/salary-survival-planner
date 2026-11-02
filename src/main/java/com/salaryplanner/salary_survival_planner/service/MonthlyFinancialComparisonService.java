package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.dto.MonthlyFinancialComparison;
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
public class MonthlyFinancialComparisonService {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private FinancialCommitmentRepository commitmentRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private SavingsGoalRepository savingsGoalRepository;

    public List<MonthlyFinancialComparison> compareMonths(
            Long userId,
            int previousMonth,
            int previousYear,
            int currentMonth,
            int currentYear) {

        BigDecimal previousIncome = calculateSalary(
                userId, previousMonth, previousYear);

        BigDecimal currentIncome = calculateSalary(
                userId, currentMonth, currentYear);

        BigDecimal previousCommitments = calculateCommitments(
                userId, previousMonth, previousYear);

        BigDecimal currentCommitments = calculateCommitments(
                userId, currentMonth, currentYear);

        BigDecimal previousExpenses = calculateExpenses(
                userId, previousMonth, previousYear);

        BigDecimal currentExpenses = calculateExpenses(
                userId, currentMonth, currentYear);

        BigDecimal previousInvestments =
                calculateInvestments(userId);

        BigDecimal currentInvestments =
                calculateInvestments(userId);

        List<MonthlyFinancialComparison> comparisons =
                new ArrayList<>();

        comparisons.add(createComparison(
                "Income", previousIncome, currentIncome));

        comparisons.add(createComparison(
                "Commitments", previousCommitments, currentCommitments));

        comparisons.add(createComparison(
                "Expenses", previousExpenses, currentExpenses));

        comparisons.add(createComparison(
                "Investments", previousInvestments, currentInvestments));

        return comparisons;
    }

    private BigDecimal calculateSalary(
            Long userId, int month, int year) {

        return salaryRepository.findByUserId(userId)
                .stream()
                .filter(s -> s.getSalaryDate().getMonthValue() == month
                        && s.getSalaryDate().getYear() == year)
                .map(s -> s.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateCommitments(
            Long userId, int month, int year) {

        return commitmentRepository.findByUserId(userId)
                .stream()
                .filter(c -> c.getDueDate().getMonthValue() == month
                        && c.getDueDate().getYear() == year)
                .map(c -> c.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateExpenses(
            Long userId, int month, int year) {

        return expenseRepository.findByUserId(userId)
                .stream()
                .filter(e -> e.getExpenseDate().getMonthValue() == month
                        && e.getExpenseDate().getYear() == year)
                .map(e -> e.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateInvestments(Long userId) {

        return savingsGoalRepository.findByUserId(userId)
                .stream()
                .map(g -> g.getInvestedAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private MonthlyFinancialComparison createComparison(
            String metric,
            BigDecimal previousAmount,
            BigDecimal currentAmount) {

        MonthlyFinancialComparison comparison =
                new MonthlyFinancialComparison();

        BigDecimal difference =
                currentAmount.subtract(previousAmount);

        BigDecimal percentageChange = BigDecimal.ZERO;

        if (previousAmount.compareTo(BigDecimal.ZERO) != 0) {
            percentageChange = difference
                    .multiply(BigDecimal.valueOf(100))
                    .divide(previousAmount, 2, RoundingMode.HALF_UP);
        }

        String trend;

        if (difference.compareTo(BigDecimal.ZERO) > 0) {
            trend = "INCREASED";
        } else if (difference.compareTo(BigDecimal.ZERO) < 0) {
            trend = "DECREASED";
        } else {
            trend = "UNCHANGED";
        }

        comparison.setMetricName(metric);
        comparison.setPreviousMonthAmount(previousAmount);
        comparison.setCurrentMonthAmount(currentAmount);
        comparison.setDifference(difference);
        comparison.setPercentageChange(percentageChange);
        comparison.setFinancialTrend(trend);

        return comparison;
    }
}