package com.aditya.documind.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aditya.documind.Common.ApiResponse;
import com.aditya.documind.DTO.AuthResponse;
import com.aditya.documind.DTO.LoginRequest;
import com.aditya.documind.DTO.RegisterRequest;
import com.aditya.documind.Security.JWTservice;
import com.aditya.documind.Service.AuthService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService=authService;
    }

    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(@RequestBody RegisterRequest request) {
        return new ApiResponse<>(true, "Registered",
                authService.register(request));
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest request){
        return new ApiResponse<>(true,"Logged in",authService.login(request));
    }
}
