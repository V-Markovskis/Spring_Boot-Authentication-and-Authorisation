package com.example.springsecurityauthwithh2.demo;

import com.example.springsecurityauthwithh2.auth.UserService;
import com.example.springsecurityauthwithh2.user.UpdateRoleRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@Tag(name = "admin permissions", description = "admin actions: get, put, post, delete")
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
    @Operation(description = "admin read option")
    @SecurityRequirement(name = "bearerAuth")
    public String get() {
        return "GET:: admin controller";
    }
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    @Operation(description = "admin create option")
    @SecurityRequirement(name = "bearerAuth")
    public String post() {
        return "POST:: admin controller";
    }
    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    @Operation(description = "admin update option")
    @SecurityRequirement(name = "bearerAuth")
    public String put() {
        return "PUT:: admin controller";
    }
    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    @Operation(description = "admin delete option")
    @SecurityRequirement(name = "bearerAuth")
    public String delete() {
        return "DELETE:: admin controller";
    }

    @PostMapping("/roles/add/{userId}")
    @PreAuthorize("hasAuthority('admin:update')")
    @Operation(description = "existing user role change option")
    @SecurityRequirement(name = "bearerAuth")
    public String addUserRole(@PathVariable int userId, @RequestBody UpdateRoleRequest request) {
        userService.addUserRole(userId, request.getRole());
        return "OK";
    }
}
