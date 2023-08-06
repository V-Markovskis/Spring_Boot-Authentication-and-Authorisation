package com.example.springsecurityauthwithh2.auth;


import com.example.springsecurityauthwithh2.repository.UserRepository;
import com.example.springsecurityauthwithh2.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUserRole(int userId, Role role) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setRole(role);
            userRepository.save(user);
        });
    }
}
