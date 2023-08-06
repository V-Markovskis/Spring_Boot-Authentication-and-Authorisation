package com.example.springsecurityauthwithh2.auth;


import com.example.springsecurityauthwithh2.validation.AuthenticationRequestValidator;
import com.example.springsecurityauthwithh2.config.JwtService;
import com.example.springsecurityauthwithh2.repository.UserRepository;
import com.example.springsecurityauthwithh2.user.Role;
import com.example.springsecurityauthwithh2.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private final List<AuthenticationRequestValidator> authenticationRequestValidators;

    //method returns the authenticationResponse
    //method allows us to create a user => save it to the DB => return generated token out of it
    public AuthenticationResponse register(RegisterRequest request) {

        //validate registration process (email format, password length, same email registration)
        authenticationRequestValidators.forEach(validator -> validator.validate(request));

        //create user object out of this RegisterRequest
        var user = User.builder()
                //to build this user out of this register request
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                //roles assigned by admin
                .role(request.getRole() == null ? Role.USER : request.getRole())
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
