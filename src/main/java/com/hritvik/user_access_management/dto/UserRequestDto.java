package com.hritvik.user_access_management.dto;

import com.hritvik.user_access_management.constant.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class UserRequestDto {

    @NotBlank(message = "please provide a valid user name")
    private String username;

    @NotBlank(message = "please provide a valid email")
    @Email(message = "please provide a valid email")
    private String email;

    @NotBlank(message = "please provide a valid name")
    private String name;

    @NotBlank(message = "please provide a valid password")
    @Pattern(regexp = "(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{6,20}",
            message = "Password must be 6-20 characters long, contain at least one uppercase letter, one digit, and one special character.")
    private String password;

    @NotNull(message = "please provide a valid role")
    private Role role;

}
