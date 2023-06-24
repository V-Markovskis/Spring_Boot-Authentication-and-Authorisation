package com.example.springsecurityauthwithh2.repository;

import com.example.springsecurityauthwithh2.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    //method to retrieve or find a user by email, because email is unique
    Optional<User> findByEmail(String email);
}
