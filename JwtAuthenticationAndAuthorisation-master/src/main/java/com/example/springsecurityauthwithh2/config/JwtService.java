package com.example.springsecurityauthwithh2.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//@Service => transform class to a managed bean
@Service
public class JwtService {

    private static final String SECRET_KEY = "635166546A576E5A7234753778214125442A472D4B614E645267556B58703273";

    //method that will return a string username and
    //it takes as a parameter string token => expl.(jwt)
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //method to extract single claim that we pass
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        //extract all claims
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //generate token out of userDetails only
    public String generateToken(UserDetails userDetails) {
        //return empty HashMap and userDetails
        return generateToken(new HashMap<>(), userDetails);
    }

    //method to generate token out of extraClaims and userDetails
    public String generateToken(
            //Map that contains the claims that we want to add
            //if I want to pass authorities or any info
            Map<String,Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                //means when this claim was created and this info will help
                //us to calculate the expiration date or to check if the token is still valid or not
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                //generate and return the token
                .compact();
    }

    //implement method to validate a token
    //check in parameters => if this token belongs to this userDetails
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        //make sure that username we have within the token
        //is the same as the username we have as input
        // && need to check that my token is not expired
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    //implement token from input
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    //extract expiration date
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //method to extract all the claims
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                //setSigningKey is a secret that is used to digitally sign the JWT
                //used to create the signature part of the JWT
                //which is used to verify that sender of the JWT is
                //who it claims to be and ensure that the message wasn't changed along the way
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                //in .getBody() we get all claims
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
