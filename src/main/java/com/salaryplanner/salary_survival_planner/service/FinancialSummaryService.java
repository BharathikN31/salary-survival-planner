package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.dto.FinancialSummary;
import com.salaryplanner.salary_survival_planner.entity.*;
import com.salaryplanner.salary_survival_planner.repository.SalaryRepository;
import com.salaryplanner.salary_survival_planner.repository.FinancialCommitmentRepository;
import com.salaryplanner.salary_survival_planner.repository.ExpenseRepository;
import com.salaryplanner.salary_survival_planner.repository.BudgetRepository;
import com.salaryplanner.salary_survival_planner.repository.SavingsGoalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class FinancialSummaryService {

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

    public BigDecimal calculateTotalSalary(Long userId) {
        List<Salary> salaries = salaryRepository.findByUserId(userId);

        BigDecimal totalSalary = BigDecimal.ZERO;

        for (Salary salary : salaries) {
            totalSalary = totalSalary.add(salary.getAmount());
        }

        return totalSalary;
    }

    public BigDecimal calculateTotalCommitments(Long userId) {
        List<FinancialCommitment> commitments = commitmentRepository.findByUserId(userId);
        BigDecimal totalCommitment = BigDecimal.ZERO;
        for (FinancialCommitment commitment : commitments) {
            totalCommitment = totalCommitment.add(commitment.getAmount());
        }
        return totalCommitment;
    }

    public BigDecimal calculateTotalExpenses(Long userId) {
        List<Expense> expenses = expenseRepository.findByUserId(userId);
        BigDecimal totalExpense = BigDecimal.ZERO;
        for (Expense expense : expenses) {
            totalExpense = totalExpense.add(expense.getAmount());
        }
        return totalExpense;
    }

    public BigDecimal calculateTotalBudgetAllocated(Long userId) {
        List<Budget> budgets = budgetRepository.findByUserId(userId);
        BigDecimal totalBudget = BigDecimal.ZERO;
        for (Budget budget : budgets) {
            totalBudget = totalBudget.add(budget.getMonthlyLimit());
        }
        return totalBudget;
    }

    public BigDecimal calculateTotalInvested(Long userId) {
        List<SavingsGoal> savingsGoals = savingsGoalRepository.findByUserId(userId);
        BigDecimal totalSavings = BigDecimal.ZERO;
        for (SavingsGoal savingsGoal : savingsGoals) {
            totalSavings = totalSavings.add(savingsGoal.getInvestedAmount());
        }
        return totalSavings;
    }


    public BigDecimal calculateRemainingBalance(Long userId) {

        BigDecimal remainingBalance =calculateTotalSalary(userId).subtract(calculateTotalCommitments(userId)).subtract(calculateTotalExpenses(userId));
        return  remainingBalance;
    }

    public BigDecimal calculateSafeDailyLimit(Long userId) {
        BigDecimal remainingBalance = calculateRemainingBalance(userId);

        int daysLeft = LocalDate.now().lengthOfMonth()
                - LocalDate.now().getDayOfMonth() + 1;

        if (remainingBalance.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        return remainingBalance.divide(
                BigDecimal.valueOf(daysLeft),
                2,
                RoundingMode.DOWN
        );
    }public Long calculateDaysUntilSalary(Long userId) {
        List<Salary> salaries = salaryRepository.findByUserId(userId);

        if (salaries.isEmpty()) {
            return null;
        }

        LocalDate today = LocalDate.now();

        LocalDate lastSalaryDate = salaries.stream()
                .map(Salary::getSalaryDate)
                .max(LocalDate::compareTo)
                .orElse(null);

        if (lastSalaryDate == null) {
            return null;
        }

        LocalDate nextSalaryDate = lastSalaryDate.withDayOfMonth(
                Math.min(lastSalaryDate.getDayOfMonth(),
                        lastSalaryDate.lengthOfMonth())
        ).withYear(today.getYear()).withMonth(today.getMonthValue());

        if (!nextSalaryDate.isAfter(today)) {
            nextSalaryDate = nextSalaryDate.plusMonths(1);
        }

        return ChronoUnit.DAYS.between(today, nextSalaryDate);
    }

    public FinancialSummary getFinancialSummary(Long userId) {
        FinancialSummary summary = new FinancialSummary();

        summary.setUserId(userId);
        summary.setTotalSalary(calculateTotalSalary(userId));
        summary.setTotalCommitments(calculateTotalCommitments(userId));
        summary.setTotalExpenses(calculateTotalExpenses(userId));
        summary.setTotalBudgetAllocated(calculateTotalBudgetAllocated(userId));
        summary.setTotalInvested(calculateTotalInvested(userId));
        summary.setRemainingBalance(calculateRemainingBalance(userId));
        summary.setSafeDailyLimit(calculateSafeDailyLimit(userId));
        summary.setDaysUntilSalary(calculateDaysUntilSalary(userId));

        return summary;
    }
}
