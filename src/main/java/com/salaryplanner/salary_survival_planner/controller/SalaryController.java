package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.entity.Salary;
import com.salaryplanner.salary_survival_planner.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salaries")
public class SalaryController {
    @Autowired
    private SalaryService salaryService;

    @GetMapping
    public ResponseEntity<List<Salary>> getAllSalaries(){
        return  new ResponseEntity<>(salaryService.getAllSalaries(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Salary> getSalaryById(@PathVariable Long id){
        return  new ResponseEntity<>(salaryService.getSalaryById(id),HttpStatus.OK);
    }

    @PostMapping
    public  ResponseEntity<Salary> addSalary(@RequestBody Salary newSalary){
        return new ResponseEntity<>(salaryService.addSalary(newSalary),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalary(@PathVariable Long id){
        salaryService.deleteSalary(id);
        return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

