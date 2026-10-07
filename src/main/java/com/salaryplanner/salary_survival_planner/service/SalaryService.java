package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.entity.Salary;
import com.salaryplanner.salary_survival_planner.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    public Salary addSalary(Salary newSalary){
        return salaryRepository.save(newSalary);
    }

    public Salary getSalaryById(Long id){
        return  salaryRepository.findById(id).orElse(null);
    }

    public List<Salary> getAllSalaries(){
        return  salaryRepository.findAll();
    }

    public void deleteSalary(Long id){
        salaryRepository.deleteById(id);
    }
}
