package com.example.springsecurityauthwithh2.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//provide endpoints were where user can create acc and authenticate
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "registration and authentication service", description = "the authentication API with register and authentication options")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;


    @PostMapping("/register")
    @Operation(description = "registration request")
    public ResponseEntity<AuthenticationResponse> register(
            //body which hold all the requests or the registration info
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    @Operation(description = "authentication request")
    public ResponseEntity<AuthenticationResponse> authenticate (
            //body which hold all the requests or the registration info
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
