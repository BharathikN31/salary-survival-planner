
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

    @Autowired
    private CurrentUserService currentUserService;

    // Add a bank account
    public BankAccount addAccount(BankAccount bankAccount) {

        Long userId = currentUserService.getCurrentUserId();

        bankAccount.setUserId(userId);
        bankAccount.setId(null);

        return bankAccountRepository.save(bankAccount);
    }

    // Get all accounts belonging to logged-in user
    public List<BankAccount> getAccountsByUserId() {

        Long userId = currentUserService.getCurrentUserId();

        return bankAccountRepository.findByUserId(userId);
    }

    // Get an account only if it belongs to logged-in user
    public BankAccount getAccountById(Long accountId) {

        Long userId = currentUserService.getCurrentUserId();

        return bankAccountRepository
                .findByIdAndUserId(accountId, userId)
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

    // Calculate total balance for logged-in user
    public BigDecimal getTotalBalance() {

        Long userId = currentUserService.getCurrentUserId();

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
    // Delete only an account belonging to logged-in user
    public void deleteAccount(Long accountId) {

        BankAccount bankAccount = getAccountById(accountId);

        bankAccountRepository.delete(bankAccount);
    }
}
