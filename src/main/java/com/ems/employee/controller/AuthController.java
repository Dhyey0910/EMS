package com.ems.employee.controller;

import com.ems.employee.dto.auth.LoginRequestDTO;
import com.ems.employee.dto.auth.LoginResponseDTO;
import com.ems.employee.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/login")
    public LoginResponseDTO login(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        return authService.login(loginRequestDTO);
    }
}