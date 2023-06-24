package com.example.springsecurityauthwithh2.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
//build object in easy way using the design pattern Builder
@Builder
@NoArgsConstructor
@AllArgsConstructor
//make class for entity
@Entity
@Table(name = "users")
//when Spring Security starts and set up the application it will use an object
//called user details and this user details is an interface that contains a bunch of methods
public class User implements UserDetails {
    //@Id => telling that id below is the unique identifier for this class user
    @Id
    //auto increment id
    @GeneratedValue
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    //@Enumerated tell to spring boot that this is enum
    @Enumerated(EnumType.STRING)
    private Role role;

    //returns list of roles
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        //true - cuz nonExpired should be true
        //otherwise we will not be able to connect our users
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
