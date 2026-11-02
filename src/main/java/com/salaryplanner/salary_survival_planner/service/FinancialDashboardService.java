package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.dto.FinancialDashboard;
import com.salaryplanner.salary_survival_planner.dto.FinancialSummary;
import com.salaryplanner.salary_survival_planner.dto.SavingsGoalProgress;
import com.salaryplanner.salary_survival_planner.dto.OverspendingAlert;
import com.salaryplanner.salary_survival_planner.dto.PaymentReminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FinancialDashboardService {

    @Autowired
    private FinancialSummaryService financialSummaryService;

    @Autowired
    private SavingsGoalProgressService savingsGoalProgressService;

    @Autowired
    private OverspendingAlertService overspendingAlertService;

    @Autowired
    private PaymentReminderService paymentReminderService;

    @Autowired
    private BankAccountService bankAccountService;

    public FinancialDashboard getDashboard(Long userId) {

        FinancialDashboard dashboard = new FinancialDashboard();

        dashboard.setUserId(userId);

        FinancialSummary summary =
                financialSummaryService.getFinancialSummary(userId);

        List<SavingsGoalProgress> savingsGoals =
                savingsGoalProgressService.getSavingsGoalProgress(userId);

        List<OverspendingAlert> alerts =
                overspendingAlertService.getOverspendingAlerts(userId);

        List<PaymentReminder> reminders =
                paymentReminderService.getPaymentReminders(userId);

        dashboard.setFinancialSummary(summary);
        dashboard.setSavingsGoals(savingsGoals);
        dashboard.setOverspendingAlerts(alerts);
        dashboard.setPaymentReminders(reminders);

        BigDecimal totalBankBalance =
                bankAccountService.getTotalBalance(userId);

        dashboard.setTotalBankBalance(totalBankBalance);
        return dashboard;
    }
}