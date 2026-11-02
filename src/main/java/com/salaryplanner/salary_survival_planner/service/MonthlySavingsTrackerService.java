package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.dto.MonthlySavingsSummary;
import com.salaryplanner.salary_survival_planner.entity.MonthlySavingsTarget;
import com.salaryplanner.salary_survival_planner.entity.InvestmentTransaction;
import com.salaryplanner.salary_survival_planner.repository.MonthlySavingsTargetRepository;
import com.salaryplanner.salary_survival_planner.repository.InvestmentTransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MonthlySavingsTrackerService {

    @Autowired
    private MonthlySavingsTargetRepository targetRepository;

    @Autowired
    private InvestmentTransactionRepository transactionRepository;


    public MonthlySavingsTarget saveTarget(MonthlySavingsTarget target) {

        Optional<MonthlySavingsTarget> existingTarget =
                targetRepository.findByUserIdAndTargetYearAndTargetMonth(
                        target.getUserId(),
                        target.getTargetYear(),
                        target.getTargetMonth()
                );

        if (existingTarget.isPresent()) {

            MonthlySavingsTarget savedTarget = existingTarget.get();

            savedTarget.setTargetAmount(target.getTargetAmount());

            return targetRepository.save(savedTarget);
        }

        return targetRepository.save(target);
    }


    public MonthlySavingsSummary getMonthlySavingsSummary(
            Long userId,
            int year,
            int month) {

        MonthlySavingsSummary summary = new MonthlySavingsSummary();

        summary.setUserId(userId);
        summary.setYear(year);
        summary.setMonth(month);

        BigDecimal targetAmount = BigDecimal.ZERO;
        BigDecimal totalContributions = BigDecimal.ZERO;
        BigDecimal totalWithdrawals = BigDecimal.ZERO;

        Optional<MonthlySavingsTarget> target =
                targetRepository.findByUserIdAndTargetYearAndTargetMonth(
                        userId,
                        year,
                        month
                );

        if (target.isPresent()) {
            targetAmount = target.get().getTargetAmount();
        }

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(
                startDate.lengthOfMonth()
        );

        List<InvestmentTransaction> transactions =
                transactionRepository.findByUserIdAndTransactionDateBetween(
                        userId,
                        startDate,
                        endDate
                );

        for (InvestmentTransaction transaction : transactions) {

            if (transaction.getAmount() == null
                    || transaction.getTransactionType() == null) {
                continue;
            }

            String type = transaction.getTransactionType()
                    .trim()
                    .toUpperCase();

            if (type.equals("CONTRIBUTION")) {

                totalContributions =
                        totalContributions.add(transaction.getAmount());

            } else if (type.equals("WITHDRAWAL")) {

                totalWithdrawals =
                        totalWithdrawals.add(transaction.getAmount());
            }
        }

        BigDecimal netSavings =
                totalContributions.subtract(totalWithdrawals);

        BigDecimal remainingAmount =
                targetAmount.subtract(netSavings);

        if (remainingAmount.compareTo(BigDecimal.ZERO) < 0) {
            remainingAmount = BigDecimal.ZERO;
        }

        BigDecimal completionPercentage = BigDecimal.ZERO;

        if (targetAmount.compareTo(BigDecimal.ZERO) > 0) {

            completionPercentage = netSavings
                    .multiply(BigDecimal.valueOf(100))
                    .divide(targetAmount, 2, RoundingMode.HALF_UP);

            if (completionPercentage.compareTo(BigDecimal.ZERO) < 0) {
                completionPercentage = BigDecimal.ZERO;
            }

            if (completionPercentage.compareTo(
                    BigDecimal.valueOf(100)) > 0) {
                completionPercentage = BigDecimal.valueOf(100);
            }
        }

        String status;

        if (targetAmount.compareTo(BigDecimal.ZERO) <= 0) {
            status = "TARGET NOT SET";
        } else if (netSavings.compareTo(targetAmount) >= 0) {
            status = "TARGET ACHIEVED";
        } else {
            status = "IN PROGRESS";
        }

        summary.setTargetAmount(targetAmount);
        summary.setTotalContributions(totalContributions);
        summary.setTotalWithdrawals(totalWithdrawals);
        summary.setNetSavings(netSavings);
        summary.setRemainingAmount(remainingAmount);
        summary.setCompletionPercentage(completionPercentage);
        summary.setStatus(status);

        return summary;
    }
}