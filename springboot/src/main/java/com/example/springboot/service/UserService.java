package com.example.springboot.service;

import com.example.springboot.context.SaltedMD5Password;
import com.example.springboot.dto.UserDTO;
import com.example.springboot.exception.UserNotFoundException;
import com.example.springboot.model.Privilege;
import com.example.springboot.model.Role;
import com.example.springboot.model.User;
import com.example.springboot.repository.RoleRepository;
import com.example.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsManager {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User by id: " + id + " was not found"));
    }

//    public Iterable<User> findUserByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
//
//    public Map<String, String> checkLogin(String username, String password){
//        Iterable<User> user = this.findUserByUsername(username);
//        Map<String, String> status = new HashMap<>();
//        for (User x: user) {
//            if(x.getUsername().equals(username) && isPasswordTrue(password, x.getPassword(), x.getSalt())) {
//                status.put("status", "success");
//                status.put("id", x.getId() + "");
//                status.put("role", x.getRole() + "");
//                status.put("username", x.getUsername());
//                return status;
//            }
//        }
//        status.put("status","fail");
//        return status;
//    }

    public boolean isPasswordTrue(String password, String hasspass, String salt){
        SaltedMD5Password md5 = new SaltedMD5Password();
        String clientHashPass = md5.getSecurePassword(password, salt);
        if(clientHashPass.equals(hasspass)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void createUser(UserDetails user){
        userRepository.save((User) user);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return new org.springframework.security.core.userdetails.User(
                    " ", " ", true, true, true, true,
                    getAuthorities(Arrays.asList(
                            roleRepository.findByName("ROLE_USER"))));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.isEnabled(), true, true,
                true, getAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    public UserDetails registerNewUserAccount(final UserDTO accountDTO) {
        if (userExists(accountDTO.getUsername())) {
            throw new RuntimeException("User Already: " + accountDTO.getUsername());
        }
        final User user = new User();
        user.setUsername(accountDTO.getUsername());
        user.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        createUser(user);
        return user;
    }
}
