
package com.salaryplanner.salary_survival_planner.controller;

import com.salaryplanner.salary_survival_planner.dto.RegisterRequest;
import com.salaryplanner.salary_survival_planner.dto.UpdateUserRequest;
import com.salaryplanner.salary_survival_planner.dto.UserResponse;
import com.salaryplanner.salary_survival_planner.entity.User;
import com.salaryplanner.salary_survival_planner.service.CurrentUserService;
import com.salaryplanner.salary_survival_planner.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CurrentUserService currentUserService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {

        validateOwnership(id);

        User user = userService.getUserById(id);

        UserResponse response = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(
            @Valid @RequestBody RegisterRequest request) {

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());

        User savedUser = userService.registerUser(newUser);

        UserResponse response = new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {

        validateOwnership(id);

        User modifiedUser = new User();
        modifiedUser.setName(request.getName());

        User updatedUser = userService.updateUserName(id, modifiedUser);

        UserResponse response = new UserResponse(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        validateOwnership(id);

        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void validateOwnership(Long requestedUserId) {

        Long loggedInUserId = currentUserService.getCurrentUserId();

        if (!requestedUserId.equals(loggedInUserId)) {
            throw new AccessDeniedException(
                    "You are not authorized to access this user's data"
            );
        }
    }
}
