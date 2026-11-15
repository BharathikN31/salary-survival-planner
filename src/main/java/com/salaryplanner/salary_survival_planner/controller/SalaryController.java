
package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.entity.Salary;
import com.salaryplanner.salary_survival_planner.service.SalaryService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salaries")
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    // Get all salaries belonging to the logged-in user
    @GetMapping
    public ResponseEntity<List<Salary>> getAllSalaries() {
        return ResponseEntity.ok(salaryService.getAllSalaries());
    }

    // Get a salary by ID
    @GetMapping("/{id}")
    public ResponseEntity<Salary> getSalaryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(salaryService.getSalaryById(id));
    }

    // Add a new salary
    @PostMapping
    public ResponseEntity<Salary> addSalary(
            @Valid @RequestBody Salary newSalary) {

        return new ResponseEntity<>(
                salaryService.addSalary(newSalary),
                HttpStatus.CREATED
        );
    }

    // Delete a salary
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalary(
            @PathVariable Long id) {

        salaryService.deleteSalary(id);

        return ResponseEntity.noContent().build();
    }
}
