package org.demo.eventtracker.API.controller;

import org.demo.eventtracker.API.dto.LoginRequest;
import org.demo.eventtracker.API.dto.LoginResponse;
import org.demo.eventtracker.API.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}