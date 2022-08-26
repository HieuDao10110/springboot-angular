package com.example.springboot.service;

import com.example.springboot.exception.UserNotFoundException;
import com.example.springboot.model.User;
import com.example.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

    public Map<String, String> checkLogin(String username, String password){
        Iterable<User> user = this.getAllUsers();
        Map<String, String> status = new HashMap<>();
        for (User x: user) {
            if(x.getUsername().equals(username) && x.getPassword().equals(password)) {
                status.put("status", "success");
                status.put("id", x.getId() + "");
                status.put("role", x.getRole() + "");
                status.put("username", x.getUsername());
                return status;
            }
        }
        status.put("status","fail");
        return status;
    }
}
