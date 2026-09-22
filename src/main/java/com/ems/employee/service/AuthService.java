package com.ems.employee.service;

import com.ems.employee.dto.auth.LoginRequestDTO;
import com.ems.employee.dto.auth.LoginResponseDTO;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.ems.employee.security.JwtService;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword()
                );

        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

        String email = authentication.getName();

        String role = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority()
                .replace("ROLE_", "");

        String token = jwtService.generateToken(email, role);

        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setToken(token);

        return responseDTO;
    }
}