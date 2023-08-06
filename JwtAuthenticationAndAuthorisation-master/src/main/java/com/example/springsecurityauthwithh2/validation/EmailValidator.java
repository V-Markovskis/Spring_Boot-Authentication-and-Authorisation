package com.example.springsecurityauthwithh2.validation;

import com.example.springsecurityauthwithh2.auth.RegisterRequest;
import com.example.springsecurityauthwithh2.exceptions.InvalidEmailFormatException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailValidator implements AuthenticationRequestValidator {
    @Override
    public void validate(RegisterRequest request) {
        if(!isValidEmail(request.getEmail())) {
            throw new InvalidEmailFormatException("Invalid email format");
        }
    }
    private boolean isValidEmail(String email) {
        //Regular expression for email validation
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
