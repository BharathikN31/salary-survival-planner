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

    public FinancialCommitment addCommitment(FinancialCommitment newCommitment) {
        return financialCommitmentRepository.save(newCommitment);
    }

    public FinancialCommitment getCommitmentById(Long id) {
        return financialCommitmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Financial commitment not found with ID: " + id
                ));
    }

    public List<FinancialCommitment> getAllCommitments() {
        return financialCommitmentRepository.findAll();
    }

    public void deleteCommitment(Long id) {

        FinancialCommitment existingCommitment =
                financialCommitmentRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Financial commitment not found with ID: " + id
                        ));

        financialCommitmentRepository.delete(existingCommitment);
    }
}