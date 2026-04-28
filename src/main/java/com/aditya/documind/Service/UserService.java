package com.aditya.documind.Service;

import com.aditya.documind.DTO.UserResponse;
import com.aditya.documind.Entity.User;
import com.aditya.documind.Repository.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo){
        this.userRepo=userRepo;
    }
    
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        // The Aspect triggers here, enabling the filter on the session
        return userRepo.findAll().stream()
            .map(this::mapToResponse)
            .toList();
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .organizationName(user.getOrganization().getName())
                .build();
    }
}

