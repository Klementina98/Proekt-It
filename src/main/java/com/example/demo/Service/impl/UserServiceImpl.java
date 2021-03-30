package com.example.demo.Service.impl;

import com.example.demo.Exceptions.InvalidUsernameException;
import com.example.demo.Model.MyUser;
import com.example.demo.Model.Role;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import lombok.SneakyThrows;
import org.openqa.selenium.InvalidArgumentException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public MyUser create(String username, String password,Role role) {
        return this.userRepository.save(new MyUser(username,passwordEncoder.encode(password),role));
    }

    @Override
    public MyUser login(String username, String password) throws InvalidUsernameException {
        if (username==null || username.isEmpty() || password==null || password.isEmpty()) {
            throw new InvalidArgumentException("Wrong credentials");
        }
        return userRepository.findByUsernameAndPassword(username,password).orElseThrow(InvalidUsernameException::new);
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user= this.userRepository.findById(username).orElseThrow(InvalidUsernameException::new);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), Stream.of(new SimpleGrantedAuthority(user.getRole().toString())).collect(Collectors.toList()));
        return userDetails;
    }
}
