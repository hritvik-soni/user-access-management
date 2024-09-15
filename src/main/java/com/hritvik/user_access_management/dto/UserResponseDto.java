package com.hritvik.user_access_management.dto;

import com.hritvik.user_access_management.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String name;
    private Role role;
    private LocalDateTime createdTs;
    private LocalDateTime updatedTs;
}
