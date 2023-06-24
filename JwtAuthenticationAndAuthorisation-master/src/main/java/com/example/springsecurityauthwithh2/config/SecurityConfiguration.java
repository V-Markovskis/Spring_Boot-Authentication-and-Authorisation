package com.example.springsecurityauthwithh2.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@Configuration and @EnableWebSecurity need to be together when we work with springboot 3.0
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    //Spring security try to look for bean SecurityFilterChain
    //SecurityFilterChain bean responsible for configuration all the HTTP security of our application
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //disable csrf() verification
                .csrf()
                .disable()
                //implement the real configuration
                //within the security app we can add whitelist
                //whitelist means that we have some endpoints that do not require any authentication or any tokens
                .authorizeHttpRequests()
                //authorize all request within below list
                .requestMatchers("/api/v1/auth/**")
                .permitAll()
                //any other requests should be authenticated
                .anyRequest()
                .authenticated()
                .and()
                //configure session management
                .sessionManagement()
                //how we want to create our session
                //spring will create new session for each request
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //tell spring which authentication provider that I want to use
                .authenticationProvider(authenticationProvider)
                //use JWT filter that we created
                //use .addFilterBefore cuz I want to execute this filter before
                //the filter called username password authentication
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        //http.build() may throw and Exception => add
        return http.build();

    }
}
