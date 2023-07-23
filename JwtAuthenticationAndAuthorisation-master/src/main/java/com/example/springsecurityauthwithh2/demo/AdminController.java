package com.example.springsecurityauthwithh2.demo;

import com.example.springsecurityauthwithh2.auth.UserService;
import com.example.springsecurityauthwithh2.demo.requests.UpdateRoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
//below annotation checks whether the current user has right to access the AdminController
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public String get() {
        return "GET:: admin controller";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public String post() {
        return "POST:: admin controller";
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public String put() {
        return "PUT:: admin controller";
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    public String delete() {
        return "DELETE:: admin controller";
    }

    @PostMapping("/roles/add/{userId}")
    @PreAuthorize("hasAuthority('admin:update')")
    public String addUserRole(@PathVariable int userId, @RequestBody UpdateRoleRequest request) {
        userService.addUserRole(userId, request.getRole());
        return "OK";
    }
}
