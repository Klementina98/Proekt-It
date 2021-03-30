package com.example.demo.Repository;

import com.example.demo.Model.MyUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MyUser, String> {
    Optional<MyUser> findByUsernameAndPassword(String username, String password);
    Optional<MyUser> findByUsername(String username);
}
