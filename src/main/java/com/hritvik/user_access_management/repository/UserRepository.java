package com.hritvik.user_access_management.repository;

import com.hritvik.user_access_management.dto.UserResponseDto;
import com.hritvik.user_access_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT new com.hritvik.user_access_management.dto.UserResponseDto(u.id, u.username, u.email, u.name, u.role,u.createdTs,u.updatedTs) " +
            "FROM User u")
    List<UserResponseDto> findAllWithDto();
}
