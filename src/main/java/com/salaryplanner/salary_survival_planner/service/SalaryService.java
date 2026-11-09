package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.entity.Salary;
import com.salaryplanner.salary_survival_planner.exception.ResourceNotFoundException;
import com.salaryplanner.salary_survival_planner.repository.SalaryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private CurrentUserService currentUserService;

    public Salary addSalary(Salary newSalary) {

        Long userId = currentUserService.getCurrentUserId();

        newSalary.setUserId(userId);

        return salaryRepository.save(newSalary);
    }

    public Salary getSalaryById(Long id) {

        Long userId = currentUserService.getCurrentUserId();

        return salaryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Salary not found with ID: " + id
                ));
    }

    public List<Salary> getAllSalaries() {

        Long userId = currentUserService.getCurrentUserId();

        return salaryRepository.findByUserId(userId);
    }

    public void deleteSalary(Long id) {

        Salary existingSalary = getSalaryById(id);

        salaryRepository.delete(existingSalary);
    }
}