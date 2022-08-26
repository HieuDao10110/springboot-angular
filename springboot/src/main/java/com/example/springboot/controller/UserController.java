package com.example.springboot.controller;

import com.example.springboot.model.User;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", exposedHeaders = "Authorization")
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    // get all user
    @GetMapping(value = "/get")
    public Iterable<User> getAllEmployees(){
        return userService.getAllUsers();
    }

    // get user by id
    @GetMapping("/get/{id}")
    public ResponseEntity<User> getEmployeeById(@PathVariable int id) {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Map> checkLogin(@RequestParam String username,@RequestParam String password){
        Map user = userService.checkLogin(username, password);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/login/api")
    public ResponseEntity<Map> checkLoginApi(@RequestBody User userDetail){
        Map user = userService.checkLogin(userDetail.getUsername(), userDetail.getPassword());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
