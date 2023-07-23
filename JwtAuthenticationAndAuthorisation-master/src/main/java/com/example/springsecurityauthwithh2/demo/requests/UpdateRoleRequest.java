package com.example.springsecurityauthwithh2.demo.requests;

import com.example.springsecurityauthwithh2.user.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleRequest {
    Role role;
}
