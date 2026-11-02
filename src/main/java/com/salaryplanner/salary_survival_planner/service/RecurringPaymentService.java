package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.dto.RecurringPayment;
import com.salaryplanner.salary_survival_planner.entity.FinancialCommitment;
import com.salaryplanner.salary_survival_planner.repository.FinancialCommitmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecurringPaymentService {

    @Autowired
    private FinancialCommitmentRepository commitmentRepository;

    public List<RecurringPayment> getUpcomingRecurringPayments(Long userId) {

        List<FinancialCommitment> commitments =
                commitmentRepository.findByUserId(userId);

        List<RecurringPayment> payments = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate upcomingLimit = today.plusDays(30);

        for (FinancialCommitment commitment : commitments) {

            if (Boolean.TRUE.equals(commitment.getRecurring())
                    && !commitment.getDueDate().isBefore(today)
                    && !commitment.getDueDate().isAfter(upcomingLimit)) {

                RecurringPayment payment = new RecurringPayment();

                payment.setUserId(userId);
                payment.setPaymentName(commitment.getName());
                payment.setAmount(commitment.getAmount());
                payment.setDueDate(commitment.getDueDate());

                long daysUntilDue = ChronoUnit.DAYS.between(
                        today, commitment.getDueDate());

                payment.setDaysUntilDue(daysUntilDue);

                if (daysUntilDue == 0) {
                    payment.setPaymentStatus("DUE TODAY");
                } else {
                    payment.setPaymentStatus("UPCOMING");
                }

                payments.add(payment);
            }
        }

        return payments;
    }
}