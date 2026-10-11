package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.entity.Expense;
import com.salaryplanner.salary_survival_planner.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody Expense newExpense){
        return  new ResponseEntity<>(expenseService.addExpense(newExpense), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Expense> getExpenseById(@PathVariable Long id){
        Expense findExpense=expenseService.getExpenseById(id);

        if(findExpense!=null){
            return new ResponseEntity<>(findExpense,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses(){
        return new ResponseEntity<>(expenseService.getAllExpenses(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id){
        expenseService.deleteExpense(id);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense updateExpense){
        Expense existingExpense =expenseService.getExpenseById(id);
        if(existingExpense !=null){
            return new ResponseEntity<>(expenseService.updateExpense(id,updateExpense),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
