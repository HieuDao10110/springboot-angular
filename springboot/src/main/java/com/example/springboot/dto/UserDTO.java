package com.example.springboot.dto;

import com.example.springboot.model.User;

public class UserDTO {
    private String id;
    private String username;

    public static UserDTO from(User user) {
        return new UserDTO(user.getUsername(), user.getPassword());
    }

    public UserDTO(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
