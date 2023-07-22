package com.example.springsecurityauthwithh2.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class ManagerController {

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin:read','manager:read')")
    public String get() {
        return "GET:: management controller";
    }
    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin:create','manager:create')")
    public String post() {
        return "POST:: management controller";
    }
    @PutMapping
    @PreAuthorize("hasAnyAuthority('admin:update','manager:update')")
    public String put() {
        return "PUT:: management controller";
    }
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('admin:delete','manager:delete')")
    public String delete() {
        return "DELETE:: management controller";
    }
}
