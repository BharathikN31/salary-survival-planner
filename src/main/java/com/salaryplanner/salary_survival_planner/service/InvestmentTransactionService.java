package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.entity.InvestmentTransaction;
import com.salaryplanner.salary_survival_planner.exception.ResourceNotFoundException;
import com.salaryplanner.salary_survival_planner.repository.InvestmentTransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InvestmentTransactionService {

    @Autowired
    private InvestmentTransactionRepository transactionRepository;

    public InvestmentTransaction addTransaction(
            InvestmentTransaction transaction) {

        return transactionRepository.save(transaction);
    }

    public List<InvestmentTransaction> getUserTransactions(Long userId) {

        return transactionRepository.findByUserId(userId);
    }

    public List<InvestmentTransaction> getGoalTransactions(
            Long savingsGoalId) {

        return transactionRepository.findBySavingsGoalId(savingsGoalId);
    }

    public List<InvestmentTransaction> getMonthlyTransactions(
            Long userId, int year, int month) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(
                startDate.lengthOfMonth());

        return transactionRepository
                .findByUserIdAndTransactionDateBetween(
                        userId, startDate, endDate);
    }

    public void deleteTransaction(Long transactionId) {

        InvestmentTransaction existingTransaction =
                transactionRepository.findById(transactionId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Investment transaction not found with ID: "
                                        + transactionId
                        ));

        transactionRepository.delete(existingTransaction);
    }
}