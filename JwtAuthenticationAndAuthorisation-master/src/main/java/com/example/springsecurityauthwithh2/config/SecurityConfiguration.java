package com.example.springsecurityauthwithh2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.springsecurityauthwithh2.user.Permission.*;
import static com.example.springsecurityauthwithh2.user.Role.ADMIN;
import static com.example.springsecurityauthwithh2.user.Role.MANAGER;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

//@Configuration and @EnableWebSecurity need to be together when we work with springboot 3.0
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//@EnableMethodSecurity
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
                .authorizeHttpRequests((authorize) -> authorize
                                .requestMatchers(antMatcher("/api/v1/auth/register")).permitAll()
                                .requestMatchers(antMatcher("/api/v1/auth/authenticate")).permitAll()
                                .requestMatchers(antMatcher("/api/v1/admin/roles/add/**")).hasAuthority(ADMIN_UPDATE.getPermission())
                                .requestMatchers(PathRequest.toH2Console()).permitAll()

                                //secured endpoint which is accessible only by ADMIN and MANAGER
//                        .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
//
//                        //securing different operations
//                        .requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
//                        .requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
//                        .requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
//                        .requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())

//                        .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
//
//                        .requestMatchers(GET, "/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
//                        .requestMatchers(POST, "/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
//                        .requestMatchers(PUT, "/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
//                        .requestMatchers(DELETE, "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())

                        .anyRequest().authenticated()
                )
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
        //Allow frames in the H2 console => allows to access console page after login
        http.headers().frameOptions().sameOrigin();
        //http.build() may throw and Exception => add
        return http.build();

    }
}
