package com.example.springsecurityauthwithh2.validators;

import com.example.springsecurityauthwithh2.auth.RegisterRequest;

public interface AuthenticationRequestValidator {
    void validate(RegisterRequest request);
}
