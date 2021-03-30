package com.example.demo.Service;

import com.example.demo.Exceptions.InvalidUsernameException;
import com.example.demo.Model.MyUser;
import com.example.demo.Model.Role;

public interface UserService {
    /**
     * (5 points) This method should create a new user. The password should be encrypted before saving.
     *
     * @param username
     * @param password
     * @param role
     * @return
     */
    MyUser create(String username, String password, Role role);
    MyUser login(String username, String password) throws InvalidUsernameException;

}
