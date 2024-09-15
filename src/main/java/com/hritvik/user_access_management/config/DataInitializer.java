package com.hritvik.user_access_management.config;

import com.hritvik.user_access_management.constant.Role;
import com.hritvik.user_access_management.dto.UserRequestDto;
import com.hritvik.user_access_management.model.User;
import com.hritvik.user_access_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;;

    @Autowired
    @Lazy
    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        UserRequestDto admin = new UserRequestDto();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setRole(Role.ADMIN);
        admin.setName("Rajesh Kumar");
        admin.setEmail("adminRajest@usermanagement.com");
        userService.createUser(admin);
    }
}
