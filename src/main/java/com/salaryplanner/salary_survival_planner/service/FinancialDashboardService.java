
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

    @Autowired
    private CurrentUserService currentUserService;

    public FinancialDashboard getDashboard() {

        Long userId = currentUserService.getCurrentUserId();

        FinancialDashboard dashboard = new FinancialDashboard();

        dashboard.setUserId(userId);

        FinancialSummary summary =
                financialSummaryService.getFinancialSummary();

        List<SavingsGoalProgress> savingsGoals =
                savingsGoalProgressService.getSavingsGoalProgress();

        List<OverspendingAlert> alerts =
                overspendingAlertService.getOverspendingAlerts();

        List<PaymentReminder> reminders =
                paymentReminderService.getPaymentReminders();

        dashboard.setFinancialSummary(summary);
        dashboard.setSavingsGoals(savingsGoals);
        dashboard.setOverspendingAlerts(alerts);
        dashboard.setPaymentReminders(reminders);

        BigDecimal totalBankBalance =
                bankAccountService.getTotalBalance();

        dashboard.setTotalBankBalance(totalBankBalance);

        return dashboard;
    }
}
