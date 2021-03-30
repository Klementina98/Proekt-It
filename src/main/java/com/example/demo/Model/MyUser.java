package com.example.demo.Model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class MyUser {

    @Id
    String username;
    String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public MyUser() {
    }

    public MyUser(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}