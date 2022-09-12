package com.example.springboot.dto;

import com.example.springboot.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String username;

    private String password;

    public static UserDTO from(User user) {
        return new UserDTO(user.getUsername(), user.getPassword());
    }

    public UserDTO(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
