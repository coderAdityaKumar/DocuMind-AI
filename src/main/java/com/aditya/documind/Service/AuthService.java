package com.aditya.documind.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aditya.documind.DTO.AuthResponse;
import com.aditya.documind.DTO.LoginRequest;
import com.aditya.documind.DTO.RegisterRequest;
import com.aditya.documind.Entity.Organization;
import com.aditya.documind.Entity.User;
import com.aditya.documind.Exception.ResourceNotFoundException;
import com.aditya.documind.Repository.OrganizationRepo;
import com.aditya.documind.Repository.UserRepo;
import com.aditya.documind.Security.JWTservice;

@Service
public class AuthService {
    private final UserRepo userRepo;
    private final OrganizationRepo organizationRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTservice jwTservice;

    public AuthService(UserRepo userRepo,OrganizationRepo organizationRepo,PasswordEncoder passwordEncoder,JWTservice jwTservice){
        this.userRepo=userRepo;
        this.jwTservice=jwTservice;
        this.organizationRepo=organizationRepo;
        this.passwordEncoder=passwordEncoder;
    }

    public AuthResponse register(RegisterRequest request){
        Organization org=Organization.builder()
        .name(request.organizationName())
        .build();

        org=organizationRepo.save(org);

        User user=User.builder()
        .name(request.name())
        .email(request.email())
        .password(passwordEncoder.encode(request.password()))
        .role("USER")
        .organization(org)
        .build();

        user=userRepo.save(user);

        String token=jwTservice.generateToken(user.getId(), org.getId(), user.getRole());

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request){
        User user=userRepo.findByEmail(request.email()).orElseThrow(()->new ResourceNotFoundException("User not found"));

        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        String token=jwTservice.generateToken(user.getId(), user.getOrganization().getId(), user.getRole());

        return new AuthResponse(token);
    }
}
