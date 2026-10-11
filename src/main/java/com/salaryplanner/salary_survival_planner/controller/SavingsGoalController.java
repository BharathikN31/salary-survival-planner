package com.salaryplanner.salary_survival_planner.controller;


import com.salaryplanner.salary_survival_planner.entity.SavingsGoal;
import com.salaryplanner.salary_survival_planner.service.SavingsGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/savings-goals")
public class SavingsGoalController {

    @Autowired
    private SavingsGoalService savingsGoalService;

    @PostMapping
    public ResponseEntity<SavingsGoal> addSavingsGoal(@RequestBody SavingsGoal newGoal){
        return new  ResponseEntity<>(savingsGoalService.addSavingsGoal(newGoal), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SavingsGoal> getSavingsGoalById(@PathVariable Long id) {
        SavingsGoal goal = savingsGoalService.getSavingsGoalById(id);

        if (goal == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(goal, HttpStatus.OK);
    }

    @GetMapping
    public  ResponseEntity<List<SavingsGoal>> getAllSavingsGoals(){
        return  new ResponseEntity<>(savingsGoalService.getAllSavingsGoals(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteSavingsGoal(@PathVariable Long id){
        savingsGoalService.deleteSavingsGoal(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<SavingsGoal> updateSavingsGoal(@PathVariable Long id,@RequestBody SavingsGoal modifyGoal){
        SavingsGoal existingGoal=savingsGoalService.getSavingsGoalById(id);

        if(existingGoal!=null){
            return new ResponseEntity<>(savingsGoalService.updateSavingsGoal(id,modifyGoal),HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
