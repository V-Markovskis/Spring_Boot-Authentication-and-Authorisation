package com.example.springsecurityauthwithh2.validators;

import com.example.springsecurityauthwithh2.auth.RegisterRequest;
import com.example.springsecurityauthwithh2.exceptions.EmailAlreadyExistsException;
import com.example.springsecurityauthwithh2.repository.UserRepository;
import com.example.springsecurityauthwithh2.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExistingUserValidator implements AuthenticationRequestValidator {

    private final UserRepository repository;

    @Autowired
    public ExistingUserValidator(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(RegisterRequest request) {
        Optional<User> existingUser = repository.findByEmail(request.getEmail());
        if(existingUser.isPresent()) {
            throw new EmailAlreadyExistsException("User with this email already registered");
        }
    }
}
