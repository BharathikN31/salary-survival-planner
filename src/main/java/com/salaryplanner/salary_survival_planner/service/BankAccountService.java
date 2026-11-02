package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.entity.BankAccount;
import com.salaryplanner.salary_survival_planner.exception.ResourceNotFoundException;
import com.salaryplanner.salary_survival_planner.repository.BankAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    // Add a bank account
    public BankAccount addAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    // Get all accounts belonging to a user
    public List<BankAccount> getAccountsByUserId(Long userId) {
        return bankAccountRepository.findByUserId(userId);
    }

    // Get a particular bank account
    public BankAccount getAccountById(Long accountId) {
        return bankAccountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bank account not found with ID: " + accountId
                        )
                );
    }

    // Update the manually recorded balance
    public BankAccount updateBalance(
            Long accountId,
            BigDecimal currentBalance) {

        BankAccount bankAccount = getAccountById(accountId);

        bankAccount.setCurrentBalance(currentBalance);

        return bankAccountRepository.save(bankAccount);
    }

    // Calculate the total balance across a user's accounts
    public BigDecimal getTotalBalance(Long userId) {

        List<BankAccount> accounts =
                bankAccountRepository.findByUserId(userId);

        BigDecimal totalBalance = BigDecimal.ZERO;

        for (BankAccount account : accounts) {
            if (account.getCurrentBalance() != null) {
                totalBalance = totalBalance.add(
                        account.getCurrentBalance()
                );
            }
        }

        return totalBalance;
    }

    // Delete a bank account
    public void deleteAccount(Long accountId) {

        BankAccount bankAccount = getAccountById(accountId);

        bankAccountRepository.delete(bankAccount);
    }
}