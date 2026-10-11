package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.dto.MonthlyFinancialReport;
import com.salaryplanner.salary_survival_planner.entity.*;
import com.salaryplanner.salary_survival_planner.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class MonthlyFinancialReportService {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private FinancialCommitmentRepository commitmentRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private SavingsGoalRepository savingsGoalRepository;

    public MonthlyFinancialReport generateMonthlyReport(
            Long userId, int month, int year) {

        MonthlyFinancialReport report = new MonthlyFinancialReport();

        report.setUserId(userId);
        report.setMonth(month);
        report.setYear(year);

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(
                startDate.lengthOfMonth());

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalCommitments = BigDecimal.ZERO;
        BigDecimal totalExpenses = BigDecimal.ZERO;
        BigDecimal totalInvested = BigDecimal.ZERO;
        BigDecimal totalBudgetAllocated = BigDecimal.ZERO;

        List<Salary> salaries =
                salaryRepository.findByUserId(userId);

        for (Salary salary : salaries) {
            if (!salary.getSalaryDate().isBefore(startDate)
                    && !salary.getSalaryDate().isAfter(endDate)) {
                totalIncome = totalIncome.add(salary.getAmount());
            }
        }

        List<FinancialCommitment> commitments =
                commitmentRepository.findByUserId(userId);

        for (FinancialCommitment commitment : commitments) {
            if (!commitment.getDueDate().isBefore(startDate)
                    && !commitment.getDueDate().isAfter(endDate)) {
                totalCommitments =
                        totalCommitments.add(commitment.getAmount());
            }
        }

        List<Expense> expenses =
                expenseRepository.findByUserId(userId);

        for (Expense expense : expenses) {
            if (!expense.getExpenseDate().isBefore(startDate)
                    && !expense.getExpenseDate().isAfter(endDate)) {
                totalExpenses = totalExpenses.add(expense.getAmount());
            }
        }

        List<SavingsGoal> savingsGoals =
                savingsGoalRepository.findByUserId(userId);

        for (SavingsGoal goal : savingsGoals) {
            if (!goal.getCreatedAt().toLocalDate().isAfter(endDate)) {
                totalInvested =
                        totalInvested.add(goal.getInvestedAmount());
            }
        }

        List<Budget> budgets =
                budgetRepository.findByUserId(userId);

        for (Budget budget : budgets) {
            if (!budget.getCreatedAt().toLocalDate().isAfter(endDate)) {
                totalBudgetAllocated =
                        totalBudgetAllocated.add(budget.getMonthlyLimit());
            }
        }

        BigDecimal remainingBalance = totalIncome
                .subtract(totalCommitments)
                .subtract(totalExpenses)
                .subtract(totalInvested);

        BigDecimal budgetUtilizationPercentage;

        if (totalBudgetAllocated.compareTo(BigDecimal.ZERO) > 0) {
            budgetUtilizationPercentage = totalExpenses
                    .multiply(BigDecimal.valueOf(100))
                    .divide(totalBudgetAllocated, 2, RoundingMode.HALF_UP);
        } else {
            budgetUtilizationPercentage = BigDecimal.ZERO;
        }

        report.setTotalIncome(totalIncome);
        report.setTotalCommitments(totalCommitments);
        report.setTotalExpenses(totalExpenses);
        report.setTotalInvested(totalInvested);
        report.setTotalBudgetAllocated(totalBudgetAllocated);
        report.setRemainingBalance(remainingBalance);
        report.setBudgetUtilizationPercentage(
                budgetUtilizationPercentage);

        return report;
    }
}