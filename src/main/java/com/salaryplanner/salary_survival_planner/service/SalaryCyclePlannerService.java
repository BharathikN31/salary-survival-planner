package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.dto.SalaryCyclePlan;
import com.salaryplanner.salary_survival_planner.entity.FinancialCommitment;
import com.salaryplanner.salary_survival_planner.entity.Salary;
import com.salaryplanner.salary_survival_planner.repository.FinancialCommitmentRepository;
import com.salaryplanner.salary_survival_planner.repository.SalaryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class SalaryCyclePlannerService {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private FinancialCommitmentRepository commitmentRepository;

    public SalaryCyclePlan createSalaryCyclePlan(
            Long userId, BigDecimal plannedSavings) {

        List<Salary> salaries = salaryRepository.findByUserId(userId);

        SalaryCyclePlan plan = new SalaryCyclePlan();
        plan.setUserId(userId);

        if (salaries.isEmpty()) {
            return plan;
        }

        Salary latestSalary = salaries.stream()
                .max((s1, s2) ->
                        s1.getSalaryDate().compareTo(s2.getSalaryDate()))
                .orElseThrow();

        BigDecimal salaryAmount = latestSalary.getAmount();

        List<FinancialCommitment> commitments =
                commitmentRepository.findByUserId(userId);

        BigDecimal totalCommitments = BigDecimal.ZERO;

        for (FinancialCommitment commitment : commitments) {
            totalCommitments =
                    totalCommitments.add(commitment.getAmount());
        }

        LocalDate salaryDate = latestSalary.getSalaryDate();
        LocalDate nextSalaryDate = salaryDate.plusMonths(1);

        while (!nextSalaryDate.isAfter(LocalDate.now())) {
            nextSalaryDate = nextSalaryDate.plusMonths(1);
        }

        long daysInSalaryCycle = ChronoUnit.DAYS.between(
                LocalDate.now(), nextSalaryDate);

        if (daysInSalaryCycle <= 0) {
            daysInSalaryCycle = 1;
        }

        BigDecimal availableForExpenses = salaryAmount
                .subtract(totalCommitments)
                .subtract(plannedSavings);

        if (availableForExpenses.compareTo(BigDecimal.ZERO) < 0) {
            availableForExpenses = BigDecimal.ZERO;
        }

        BigDecimal dailySpendingLimit = availableForExpenses.divide(
                BigDecimal.valueOf(daysInSalaryCycle),
                2,
                RoundingMode.DOWN
        );

        plan.setSalaryAmount(salaryAmount);
        plan.setTotalCommitments(totalCommitments);
        plan.setPlannedSavings(plannedSavings);
        plan.setAvailableForExpenses(availableForExpenses);
        plan.setDailySpendingLimit(dailySpendingLimit);
        plan.setDaysInSalaryCycle(daysInSalaryCycle);

        return plan;
    }
}