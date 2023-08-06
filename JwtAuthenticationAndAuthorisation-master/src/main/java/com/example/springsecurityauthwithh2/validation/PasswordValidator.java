package com.example.springsecurityauthwithh2.validation;

import com.example.springsecurityauthwithh2.auth.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator implements AuthenticationRequestValidator {
    @Override
    public void validate(RegisterRequest request) {
        if(request.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
    }


}
