package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.entity.Budget;
import com.salaryplanner.salary_survival_planner.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @PostMapping
    public ResponseEntity<Budget> addBudget(@RequestBody Budget newBudget){
        return  new ResponseEntity<>(budgetService.addBudget(newBudget), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Budget> getBudgetById(@PathVariable Long id){
        Budget findBudget=budgetService.getBudgetById(id);

        if(findBudget!=null){
            return new ResponseEntity<>(findBudget,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Budget>> getAllBudgets(){
        return new ResponseEntity<>(budgetService.getAllBudgets(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id){
        budgetService.deleteBudget(id);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudget(@PathVariable Long id, @RequestBody Budget updateBudget){
        Budget existingBudget =budgetService.getBudgetById(id);
        if(existingBudget !=null){
            return new ResponseEntity<>(budgetService.updateBudget(id,updateBudget),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
