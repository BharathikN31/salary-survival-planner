package com.salaryplanner.salary_survival_planner.controller;


import com.salaryplanner.salary_survival_planner.entity.FinancialCommitment;
import com.salaryplanner.salary_survival_planner.service.FinancialCommitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commitments")
public class FinancialCommitmentController {

    @Autowired
    private FinancialCommitmentService financialCommitmentService;

    @PostMapping
    public ResponseEntity<FinancialCommitment> addCommitment(@RequestBody FinancialCommitment newCommitment){
        return new ResponseEntity<>(financialCommitmentService.addCommitment(newCommitment), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinancialCommitment> getCommitmentById(@PathVariable Long id){
        return new ResponseEntity<>(financialCommitmentService.getCommitmentById(id),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FinancialCommitment>> getAllCommitments(){
        return new ResponseEntity<>(financialCommitmentService.getAllCommitments(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommitment(@PathVariable Long id){
        financialCommitmentService.deleteCommitment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
