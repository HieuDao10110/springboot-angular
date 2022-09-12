package com.example.springboot.spring;

import com.example.springboot.model.Privilege;
import com.example.springboot.model.Role;
import com.example.springboot.model.User;
import com.example.springboot.repository.PrivilegeRepository;
import com.example.springboot.repository.RoleRepository;
import com.example.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = true;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        Role userRole = roleRepository.findByName("ROLE_ADMIN");

        User userAdmin = new User();
//        userAdmin.setFirstName("Test");
//        userAdmin.setLastName("Test");
        userAdmin.setUsername("admin");
        userAdmin.setPassword(passwordEncoder.encode("admin"));
//        user.setEmail("test@test.com");
        userAdmin.setRoles(Arrays.asList(adminRole));
//        user.setEnabled(true);
        userRepository.save(userAdmin);


        User userUser = new User();
//        userUser.setFirstName("Test");
//        userUser.setLastName("Test");
        userUser.setUsername("user");
        userUser.setPassword(passwordEncoder.encode("user"));
//        userUser.setEmail("test@test.com");
        userUser.setRoles(Arrays.asList(userRole));
//        userUser.setEnabled(true);
        userRepository.save(userUser);
        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}