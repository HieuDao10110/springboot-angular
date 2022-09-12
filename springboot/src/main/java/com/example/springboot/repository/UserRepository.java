package com.example.springboot.repository;

import com.example.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface UserRepository extends JpaRepository<User, Long>, CrudRepository<User, Long> {
//    Iterable<User> findByUsername(String username);
    User findByUsername(String username);
    Optional<User> findById(Long id);
    boolean existsByUsername(String username);
}
