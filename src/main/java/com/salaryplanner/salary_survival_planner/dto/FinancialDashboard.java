package com.salaryplanner.salary_survival_planner.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class FinancialDashboard {

    private Long userId;

    private FinancialSummary financialSummary;
    private BigDecimal totalBankBalance;

    private List<SavingsGoalProgress> savingsGoals;

    private List<OverspendingAlert> overspendingAlerts;

    private List<PaymentReminder> paymentReminders;
}