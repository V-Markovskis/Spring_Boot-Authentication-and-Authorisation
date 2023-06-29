package com.example.springsecurityauthwithh2.auth;


import com.example.springsecurityauthwithh2.exceptions.EmailAlreadyExistsException;
import com.example.springsecurityauthwithh2.exceptions.InvalidEmailFormatException;
import com.example.springsecurityauthwithh2.config.JwtService;
import com.example.springsecurityauthwithh2.repository.UserRepository;
import com.example.springsecurityauthwithh2.user.Role;
import com.example.springsecurityauthwithh2.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//class to implement methods: register and authenticate
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    //inject our repository
    private final UserRepository repository;

    //inject our password encoder service
    private final PasswordEncoder passwordEncoder;

    //need JwtService to generate token (jwtToken)
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    //method returns the authenticationResponse
    //method allows us to create a user => save it to the DB => return generated token out of it
    public AuthenticationResponse register(RegisterRequest request) {
        String email = request.getEmail();

        //Check if user exist with this email
        Optional<User> existingUser = repository.findByEmail(email);
        if(existingUser.isPresent()) {
            throw new EmailAlreadyExistsException("User with this email already registered");
        }

        //Validate email format
        if (!isValidEmail(email)) {
            throw new InvalidEmailFormatException("Invalid email format");
        }

        //Validate password length
        if (request.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }

        //create user object out of this RegisterRequest
        var user = User.builder()
                //to build this user out of this register request
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        //to return AuthenticationResponse that contains the token create new variable:
        //use JwtService to generate token using user(object)
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                //pass the token that was generated
                .token(jwtToken)
                .build();
    }

    private boolean isValidEmail(String email) {
        //Regular expression for email validation
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher  = pattern.matcher(email);
        return matcher.matches();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                //authenticationManager takes an object of type
                //username password authentication token
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        //if I get to this part of method, means that user is authenticated
        //the username and password are correct
        //generate token and send it back
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        //once I get the user I will generate a token
        //using user object and then return AuthenticationResponse
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                //pass the token that was generated
                .token(jwtToken)
                .build();
    }
}
