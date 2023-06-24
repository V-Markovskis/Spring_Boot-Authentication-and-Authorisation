package com.example.springsecurityauthwithh2.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//the first thing that will intercept our HTTP request is the JWT authentication filter
//we want this filter to be active every time we get a request
//HTTP request = request from user

//@Component - tell spring that we want class to be managed bin
@Component
//will create a constructor using any final field that we declare below
//example - private String privateString. So @RequiredArgsConstructor will create constructor for this field
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    //userDetailsService is already an interface within the spring framework,
    //and it's from Spring framework security core
    //we want our own implementation of userDetailsService,
    //because we want to fetch our user from our database
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            //we can take data from request and provide new data within the response
            @NonNull HttpServletResponse response,
            //chain of responsibility design pattern,
            //so it will contain the list of the other filters
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        //when we make a call we need to pass the JWT authentication token
        //within the header => try to extract header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        //implement => check jwt token
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            //pass request and response to the next filter
            filterChain.doFilter(request, response);
            return;
        }
        //try to extract the token from authHeader
        //position 7 cuz => (Bearer_)
        jwt = authHeader.substring(7);

        // todo extract the userEmail from JWT token;
        //to do below, we need class that can manipulate this JWT token (class - JwtService)
        //we extracted out user name so we have now our username valid
        userEmail = jwtService.extractUsername(jwt);

        //finish validation process
        //check if user is not null && and user not authenticated already
        //if getAuthentication == null, means user is not authenticated
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //check if user is within the DB
            //and username in this case = userEmail
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            //check if token still valid
            if(jwtService.isTokenValid(jwt, userDetails)) {
                //if token is valid we need to update the SecurityContextHolder
                //and send the request to our dispatcher servlet
                //create an object of type username password authentication token
                //object is needed by Spring security context holder in order to update our security context
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                //give more details (reinforce with details of our request)
                authToken.setDetails(
                        //pass new object and buildDetails out of our HTTP request
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                //update SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            //don't forget about always calling our filter chain dot do filter
            //so we need to pass the hand to the next filters to be executed
            filterChain.doFilter(request,response);
        }
    }
}
