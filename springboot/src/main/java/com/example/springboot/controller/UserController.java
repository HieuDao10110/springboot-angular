package com.example.springboot.controller;

import com.example.springboot.config.UserRequestScopedBean;
import com.example.springboot.dto.LoginDTO;
import com.example.springboot.dto.UserDTO;
import com.example.springboot.model.User;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.security.TokenGenerator;
import com.example.springboot.service.BlackListingService;
import com.example.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", exposedHeaders = "Authorization")
@RequestMapping(value = "/api/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;
    @Autowired
    TokenGenerator tokenGenerator;

    @Autowired
    private BlackListingService blackListingService;

    @Autowired
    private UserRequestScopedBean userRequestScopedBean;
    // get all user
//    @GetMapping(value = "/get")
//    public Iterable<User> getAllEmployees(){
//        return userService.getAllUsers();
//    }

    // get user by id
//    @GetMapping("/get/{id}")
//    public ResponseEntity<User> getEmployeeById(@PathVariable int id) {
//        User user = userService.findUserById(id);
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }

//    @PostMapping(value = "/login")
//    public ResponseEntity<Map> checkLogin(@RequestParam String username,@RequestParam String password){
//        Map user = userService.checkLogin(username, password);
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }

//    @PostMapping(value = "/login/api")
//    public ResponseEntity<Map> checkLoginApi(@RequestBody User userDetail){
//        Map user = userService.checkLogin(userDetail.getUsername(), userDetail.getPassword());
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/login")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity login(){
//        Authentication authentication = daoAuthenticationProvider.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(loginDTO.getUsername(), loginDTO.getPassword()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_USER"));
        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        if(hasUserRole){
            return ResponseEntity.ok("user");
        }
        if(hasAdminRole){
            return ResponseEntity.ok("admin");
        }
        return ResponseEntity.ok("unknow");
    }
//    @GetMapping("/{username}")
//    @PreAuthorize("#user.username == #username")
//    public ResponseEntity user(@AuthenticationPrincipal User user, @PathVariable String username) {
//        log.info("user auth {}", user);
//        return ResponseEntity.ok(UserDTO.from(userRepository.findByUsername(username).orElseThrow()));
//    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/logout")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity logout() {
        blackListingService.blackListJwt(userRequestScopedBean.getJwt());
        log.info("put to cache ............ {}", userRequestScopedBean.getJwt());
        return ResponseEntity.ok("logout success");
    }
}
