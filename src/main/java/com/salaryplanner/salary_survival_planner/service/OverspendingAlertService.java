package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.dto.OverspendingAlert;
import com.salaryplanner.salary_survival_planner.entity.Budget;
import com.salaryplanner.salary_survival_planner.entity.Expense;
import com.salaryplanner.salary_survival_planner.repository.BudgetRepository;
import com.salaryplanner.salary_survival_planner.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OverspendingAlertService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<OverspendingAlert> getOverspendingAlerts(Long userId) {

        List<Budget> budgets = budgetRepository.findByUserId(userId);
        List<Expense> expenses = expenseRepository.findByUserId(userId);

        List<OverspendingAlert> alerts = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate monthStart = today.withDayOfMonth(1);
        LocalDate monthEnd = today.withDayOfMonth(today.lengthOfMonth());

        for (Budget budget : budgets) {

            BigDecimal totalSpent = BigDecimal.ZERO;

            for (Expense expense : expenses) {

                if (expense.getCategory().equalsIgnoreCase(budget.getCategory())
                        && !expense.getExpenseDate().isBefore(monthStart)
                        && !expense.getExpenseDate().isAfter(monthEnd)) {

                    totalSpent = totalSpent.add(expense.getAmount());
                }
            }

            if (totalSpent.compareTo(budget.getMonthlyLimit()) >= 0) {

                OverspendingAlert alert = new OverspendingAlert();

                alert.setUserId(userId);
                alert.setCategory(budget.getCategory());
                alert.setMonthlyLimit(budget.getMonthlyLimit());
                alert.setTotalSpent(totalSpent);

                BigDecimal overspentAmount =
                        totalSpent.subtract(budget.getMonthlyLimit());

                alert.setOverspentAmount(overspentAmount);

                if (overspentAmount.compareTo(BigDecimal.ZERO) > 0) {
                    alert.setAlertMessage(
                            "You exceeded your " + budget.getCategory()
                                    + " budget by " + overspentAmount
                    );
                } else {
                    alert.setAlertMessage(
                            "You have reached your " + budget.getCategory()
                                    + " budget limit"
                    );
                }

                alerts.add(alert);
            }
        }

        return alerts;
    }
}