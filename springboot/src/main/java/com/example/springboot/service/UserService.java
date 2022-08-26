package com.example.springboot.service;

import com.example.springboot.context.SaltedMD5Password;
import com.example.springboot.exception.UserNotFoundException;
import com.example.springboot.model.User;
import com.example.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User by id: " + id + " was not found"));
    }

    public Iterable<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Map<String, String> checkLogin(String username, String password){
        Iterable<User> user = this.findUserByUsername(username);
        Map<String, String> status = new HashMap<>();
        for (User x: user) {
            if(x.getUsername().equals(username) && isPasswordTrue(password, x.getPassword(), x.getSalt())) {
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

    public boolean isPasswordTrue(String password, String hasspass, String salt){
        SaltedMD5Password md5 = new SaltedMD5Password();
        String clientHashPass = md5.getSecurePassword(password, salt);
        if(clientHashPass.equals(hasspass)){
            return true;
        }else{
            return false;
        }
    }
}
