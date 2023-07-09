package com.example.springsecurityauthwithh2.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class Admin extends User {

    //call the constructor of the superclass User with the role of ADMIN
    public Admin(Integer id, String firstName, String lastName, String email, String password) {
        super(id, firstName, lastName, email, password, Role.ADMIN);
    }

    //getAuthorities() method overrides the method from the UserDetails interface
    //and returns a collection of objects representing user roles
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.ADMIN.name()));
    }
}
