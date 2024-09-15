package com.hritvik.user_access_management.service;

import com.hritvik.user_access_management.dto.UserRequestDto;
import com.hritvik.user_access_management.dto.UserResponseDto;
import com.hritvik.user_access_management.model.User;
import com.hritvik.user_access_management.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates a new user in the database.
     *
     * @param payload user data to be saved
     * @return ResponseEntity containing the created user object
     */
    public ResponseEntity<?> createUser(@Valid UserRequestDto payload) {
        log.info("Received Request to add user");

        log.info("converting request dto to user object");
        User user = modelMapper.map(payload, User.class);

        log.info("converting passowrd to bcrypt");
        user.setPassword(passwordEncoder.encode(payload.getPassword()));

        log.info("saving user in db");
        user = userRepository.save(user);
        log.info("saved user in db {}", user);

        log.info("returning success response and converting user object to response dto");
        UserResponseDto createdUser = modelMapper.map(user, UserResponseDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * Deletes a user in the database.
     *
     * @param id of the user to be deleted
     * @return ResponseEntity containing the result of the deletion
     */
    public ResponseEntity<?> deleteUser(Long id) {
        try {
            log.info("Received request to delete user with id {}", id);

            if (!userRepository.existsById(id)) {
                log.info("User with id {} not found", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id " + id + " not found");
            }

            log.info("deleting user with id {}", id);
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            log.error("Exception while deleting user with id {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while deleting user");
        }
    }

    /**
     * Returns a list of all users in the database.
     *
     * @return ResponseEntity containing a list of UserResponseDto, or an error message if there was an exception
     */
    public ResponseEntity<?> getAllUsers() {
        try {
            log.info("Received request to get all users");
            return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAllWithDto());
        } catch (Exception e) {
            log.error("Exception while getting all users", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while getting all users");
        }

    }

    /**
     * Returns a user from the database, if it exists.
     *
     * @param username of the user to retrieve
     * @return Optional containing the User, or empty if the user was not found
     */
    public Optional<User> getUserByUsername(String username) {
        log.info("Received request to get user with username {}", username);
        Optional<User> user = userRepository.findByUsername(username);
        log.info("Found user {}", user.get());
        return user;
    }
}
