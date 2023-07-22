package com.example.springsecurityauthwithh2.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    //for GET
    ADMIN_READ("admin:read"),

    //for PUT
    ADMIN_UPDATE("admin:update"),

    //for POST
    ADMIN_CREATE("admin:create"),

    //for DELETE
    ADMIN_DELETE("admin:delete"),


    MANAGER_READ("manager:read"),
    MANAGER_UPDATE("manager:update"),
    MANAGER_CREATE("manager:create"),
    MANAGER_DELETE("manager:delete")
    ;
    @Getter
    private final String permission;
}
