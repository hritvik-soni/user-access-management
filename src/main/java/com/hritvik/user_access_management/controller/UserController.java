package com.hritvik.user_access_management.controller;

import com.hritvik.user_access_management.dto.UserRequestDto;
import com.hritvik.user_access_management.dto.UserResponseDto;
import com.hritvik.user_access_management.model.User;
import com.hritvik.user_access_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new user.
     *
     * <p>This endpoint requires the <code>ROLE_ADMIN</code> role.
     *
     * @param user the user to create
     * @return a response entity containing the created user
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDto user) {
        return userService.createUser(user);

    }

    /**
     * Deletes a user.
     *
     * <p>This endpoint requires the <code>ROLE_ADMIN</code> role.
     *
     * @param id the id of the user to delete
     * @return a response entity containing the deleted user
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    /**
     * Returns a list of all users.
     *
     * <p>This endpoint requires either the <code>ROLE_ADMIN</code> or <code>ROLE_USER</code> role.
     *
     * @return a response entity containing a list of all users
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/list")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

}

