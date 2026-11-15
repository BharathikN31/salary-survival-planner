
package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.entity.Salary;
import com.salaryplanner.salary_survival_planner.exception.ResourceNotFoundException;
import com.salaryplanner.salary_survival_planner.repository.SalaryRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryService {

    private final SalaryRepository salaryRepository;
    private final CurrentUserService currentUserService;

    public SalaryService(
            SalaryRepository salaryRepository,
            CurrentUserService currentUserService) {

        this.salaryRepository = salaryRepository;
        this.currentUserService = currentUserService;
    }

    // Add salary
    public Salary addSalary(Salary newSalary) {

        Long userId = currentUserService.getCurrentUserId();

        // Prevent clients from supplying an existing record ID
        newSalary.setId(null);

        // Always assign the logged-in user's ID
        newSalary.setUserId(userId);

        return salaryRepository.save(newSalary);
    }

    // Get salary by ID
    public Salary getSalaryById(Long id) {

        Long userId = currentUserService.getCurrentUserId();

        return salaryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Salary not found with ID: " + id
                ));
    }

    // Get all salaries belonging to the logged-in user
    public List<Salary> getAllSalaries() {

        Long userId = currentUserService.getCurrentUserId();

        return salaryRepository.findByUserId(userId);
    }

    // Delete salary
    public void deleteSalary(Long id) {

        Salary existingSalary = getSalaryById(id);

        salaryRepository.delete(existingSalary);
    }
}
