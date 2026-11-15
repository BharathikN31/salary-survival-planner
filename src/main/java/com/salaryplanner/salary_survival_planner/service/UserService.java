
package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.entity.User;
import com.salaryplanner.salary_survival_planner.exception.EmailAlreadyExistsException;
import com.salaryplanner.salary_survival_planner.exception.ResourceNotFoundException;
import com.salaryplanner.salary_survival_planner.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User newUser) {

        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Email is already registered: " + newUser.getEmail()
            );
        }

        String encodedPassword =
                passwordEncoder.encode(newUser.getPassword());

        newUser.setPassword(encodedPassword);

        return userRepository.save(newUser);
    }

    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with ID: " + id
                ));
    }

    public User updateUserName(Long id, User modifyUser) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with ID: " + id
                ));

        existingUser.setName(modifyUser.getName());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with ID: " + id
                ));

        userRepository.delete(existingUser);
    }
}
