
package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.entity.FinancialCommitment;
import com.salaryplanner.salary_survival_planner.exception.ResourceNotFoundException;
import com.salaryplanner.salary_survival_planner.repository.FinancialCommitmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinancialCommitmentService {

    @Autowired
    private FinancialCommitmentRepository financialCommitmentRepository;

    @Autowired
    private CurrentUserService currentUserService;

    public FinancialCommitment addCommitment(
            FinancialCommitment newCommitment) {

        Long userId = currentUserService.getCurrentUserId();

        // Always generate a new database record
        newCommitment.setId(null);

        // Get user ID from authenticated JWT
        newCommitment.setUserId(userId);

        return financialCommitmentRepository.save(newCommitment);
    }

    public FinancialCommitment getCommitmentById(Long id) {

        Long userId = currentUserService.getCurrentUserId();

        return financialCommitmentRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Financial commitment not found with ID: " + id
                ));
    }

    public List<FinancialCommitment> getAllCommitments() {

        Long userId = currentUserService.getCurrentUserId();

        return financialCommitmentRepository.findByUserId(userId);
    }

    public void deleteCommitment(Long id) {

        FinancialCommitment existingCommitment = getCommitmentById(id);

        financialCommitmentRepository.delete(existingCommitment);
    }
}
