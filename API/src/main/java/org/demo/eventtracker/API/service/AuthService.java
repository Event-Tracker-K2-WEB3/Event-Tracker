package org.demo.eventtracker.API.service;

import org.demo.eventtracker.API.dto.LoginRequest;
import org.demo.eventtracker.API.dto.LoginResponse;
import org.demo.eventtracker.API.entity.AdminUser;
import org.demo.eventtracker.API.repository.AdminUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            AdminUserRepository adminUserRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        AdminUser adminUser = adminUserRepository
                .findByEmailAndEnabledTrue(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid email or password"
                ));

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                adminUser.getPasswordHash()
        );

        if (!passwordMatches) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid email or password"
            );
        }

        String token = jwtService.generateToken(adminUser);

        return new LoginResponse(
                token,
                adminUser.getId(),
                adminUser.getEmail(),
                adminUser.getFullName(),
                adminUser.getRole()
        );
    }
}