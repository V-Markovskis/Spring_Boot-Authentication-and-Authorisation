package com.example.springsecurityauthwithh2.validation;

import com.example.springsecurityauthwithh2.auth.RegisterRequest;

public interface AuthenticationRequestValidator {
    void validate(RegisterRequest request);
}
