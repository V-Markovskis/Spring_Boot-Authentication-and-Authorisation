package com.example.springsecurityauthwithh2.config;

import com.example.springsecurityauthwithh2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration annotation created for Spring
//at the startup spring will pick up this class,
//and try to implement and inject all the bins that we will declare within this ApplicationConfig
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository repository;
    //implement a bin of type UserDetailsService
    //@Bean => this method represents a bin
    @Bean
    public UserDetailsService userDetailsService() {
        //use lambda expression
        //means return new UserDetailsService,
        //and we implement the load user by username
        //lambda simplifies above provided info
        //lambda here is the username and now we need to provide an implementation => get user from DB
        return username -> repository.findByEmail(username)
                //if username not found return exception
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    //AuthenticationProvider is the data access object
    //which is responsible to fetch the UserDetails
    //and also encode password
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        //specify few properties
        //tell which UserDetailsService to use in order to fetch information about user
        authProvider.setUserDetailsService(userDetailsService());
        //provide password encoder
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    //Authentication manager responsible to manage authentication
    //AuthenticationConfiguration hold already the info about AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
