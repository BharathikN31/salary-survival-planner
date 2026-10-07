package com.salaryplanner.salary_survival_planner.service;

import com.salaryplanner.salary_survival_planner.entity.User;
import com.salaryplanner.salary_survival_planner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserService {
    @Autowired
    private  UserRepository userRepository;

    public User registerUser(User newUser){
        return  userRepository.save(newUser);
    }

    public User getUserById(Long id){
        return  userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers(){
        return  userRepository.findAll();
    }

    public User updateUserName(Long id, User modifyUser){
        User existingUser =userRepository.findById(id).orElse(null);

        if(existingUser!=null){
            existingUser.setName(modifyUser.getName());

            return userRepository.save(existingUser);
        }
        return null;
    }

    public  void deleteUser(Long id){
     userRepository.deleteById(id);
    }
}
