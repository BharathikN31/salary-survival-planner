
package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.entity.InvestmentTransaction;
import com.salaryplanner.salary_survival_planner.entity.SavingsGoal;
import com.salaryplanner.salary_survival_planner.exception.ResourceNotFoundException;
import com.salaryplanner.salary_survival_planner.repository.InvestmentTransactionRepository;
import com.salaryplanner.salary_survival_planner.repository.SavingsGoalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InvestmentTransactionService {

    @Autowired
    private InvestmentTransactionRepository transactionRepository;

    @Autowired
    private SavingsGoalRepository savingsGoalRepository;

    @Autowired
    private CurrentUserService currentUserService;

    public InvestmentTransaction addTransaction(
            InvestmentTransaction transaction) {

        Long userId = currentUserService.getCurrentUserId();

        // Verify that the savings goal belongs to this user
        SavingsGoal savingsGoal = savingsGoalRepository
                .findByIdAndUserId(transaction.getSavingsGoalId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Savings goal not found with ID: "
                                + transaction.getSavingsGoalId()));

        transaction.setUserId(userId);

        // Ensure POST creates a new transaction
        transaction.setId(null);

        return transactionRepository.save(transaction);
    }

    public List<InvestmentTransaction> getUserTransactions() {

        Long userId = currentUserService.getCurrentUserId();

        return transactionRepository.findByUserId(userId);
    }

    public List<InvestmentTransaction> getGoalTransactions(
            Long savingsGoalId) {

        Long userId = currentUserService.getCurrentUserId();

        // Return only transactions for this user's goal
        if (savingsGoalRepository.findByIdAndUserId(
                savingsGoalId, userId).isEmpty()) {
            throw new ResourceNotFoundException(
                    "Savings goal not found with ID: " + savingsGoalId);
        }

        return transactionRepository
                .findBySavingsGoalIdAndUserId(savingsGoalId, userId);
    }

    public List<InvestmentTransaction> getMonthlyTransactions(
            int year, int month) {

        Long userId = currentUserService.getCurrentUserId();

        LocalDate startDate = LocalDate.of(year, month, 1);

        LocalDate endDate = startDate.withDayOfMonth(
                startDate.lengthOfMonth());

        return transactionRepository
                .findByUserIdAndTransactionDateBetween(
                        userId, startDate, endDate);
    }

    public void deleteTransaction(Long transactionId) {

        Long userId = currentUserService.getCurrentUserId();

        InvestmentTransaction existingTransaction =
                transactionRepository.findByIdAndUserId(
                                transactionId, userId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Investment transaction not found with ID: "
                                        + transactionId));

        transactionRepository.delete(existingTransaction);
    }
}
