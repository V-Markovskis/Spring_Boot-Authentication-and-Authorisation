package com.example.springsecurityauthwithh2.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
@Tag(name = "admin and manager permissions", description = "admin and manager actions: get, put, post, delete")
public class ManagerController {

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin:read','manager:read')")
    @Operation(description = "admin or manager read option")
    @SecurityRequirement(name = "bearerAuth")
    public String get() {
        return "GET:: management controller";
    }
    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin:create','manager:create')")
    @Operation(description = "admin or manager create option")
    @SecurityRequirement(name = "bearerAuth")
    public String post() {
        return "POST:: management controller";
    }
    @PutMapping
    @PreAuthorize("hasAnyAuthority('admin:update','manager:update')")
    @Operation(description = "admin or manager update option")
    @SecurityRequirement(name = "bearerAuth")
    public String put() {
        return "PUT:: management controller";
    }
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('admin:delete','manager:delete')")
    @Operation(description = "admin or manager delete option")
    @SecurityRequirement(name = "bearerAuth")
    public String delete() {
        return "DELETE:: management controller";
    }
}
