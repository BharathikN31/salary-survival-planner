package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.dto.PaymentReminder;
import com.salaryplanner.salary_survival_planner.entity.FinancialCommitment;
import com.salaryplanner.salary_survival_planner.repository.FinancialCommitmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentReminderService {

    @Autowired
    private FinancialCommitmentRepository commitmentRepository;

    public List<PaymentReminder> getPaymentReminders(Long userId) {

        List<FinancialCommitment> commitments =
                commitmentRepository.findByUserId(userId);

        List<PaymentReminder> reminders = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate reminderLimit = today.plusDays(30);

        for (FinancialCommitment commitment : commitments) {

            LocalDate dueDate = commitment.getDueDate();

            if (dueDate == null) {
                continue;
            }

            if (dueDate.isAfter(reminderLimit)) {
                continue;
            }

            PaymentReminder reminder = new PaymentReminder();

            long daysRemaining =
                    ChronoUnit.DAYS.between(today, dueDate);

            String status;

            if (daysRemaining < 0) {
                status = "OVERDUE";
            } else if (daysRemaining == 0) {
                status = "DUE TODAY";
            } else if (daysRemaining <= 7) {
                status = "DUE SOON";
            } else {
                status = "UPCOMING";
            }

            reminder.setUserId(userId);
            reminder.setCommitmentId(commitment.getId());
            reminder.setPaymentName(commitment.getName());
            reminder.setAmount(commitment.getAmount());
            reminder.setDueDate(dueDate);
            reminder.setDaysRemaining(daysRemaining);
            reminder.setReminderStatus(status);

            reminders.add(reminder);
        }

        return reminders;
    }
}