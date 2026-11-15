
package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.entity.BankAccount;
import com.salaryplanner.salary_survival_planner.service.BankAccountService;
import com.salaryplanner.salary_survival_planner.dto.BankBalanceUpdateRequest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/bank-accounts")
@Validated
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    // Add a bank account
    @PostMapping
    public ResponseEntity<BankAccount> addAccount(
            @Valid @RequestBody BankAccount bankAccount) {

        BankAccount savedAccount =
                bankAccountService.addAccount(bankAccount);

        return new ResponseEntity<>(
                savedAccount,
                HttpStatus.CREATED
        );
    }

    // Get all accounts belonging to logged-in user
    @GetMapping
    public ResponseEntity<List<BankAccount>> getAccounts() {

        List<BankAccount> accounts =
                bankAccountService.getAccountsByUserId();

        return ResponseEntity.ok(accounts);
    }

    // Get one account belonging to logged-in user
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Bank account found"),
            @ApiResponse(responseCode = "404",
                    description = "Bank account not found"),
            @ApiResponse(responseCode = "400",
                    description = "Invalid account ID")
    })
    @GetMapping("/{accountId}")
    public ResponseEntity<BankAccount> getAccountById(
            @PathVariable
            @Positive(message = "Account ID must be positive")
            Long accountId) {

        BankAccount account =
                bankAccountService.getAccountById(accountId);

        return ResponseEntity.ok(account);
    }

    // Update the recorded balance
    @PutMapping("/{accountId}/balance")
    public ResponseEntity<BankAccount> updateBalance(
            @PathVariable Long accountId,
            @Valid @RequestBody BankBalanceUpdateRequest request) {

        BankAccount updatedAccount =
                bankAccountService.updateBalance(
                        accountId,
                        request.getCurrentBalance()
                );

        return ResponseEntity.ok(updatedAccount);
    }

    // Get total balance for logged-in user
    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalBalance() {

        BigDecimal totalBalance =
                bankAccountService.getTotalBalance();

        return ResponseEntity.ok(totalBalance);
    }

    // Delete an account belonging to logged-in user
    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(
            @PathVariable Long accountId) {

        bankAccountService.deleteAccount(accountId);

        return ResponseEntity.noContent().build();
    }
}
