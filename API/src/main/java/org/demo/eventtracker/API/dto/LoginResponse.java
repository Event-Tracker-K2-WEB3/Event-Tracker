package org.demo.eventtracker.API.dto;

public class LoginResponse {

    private String token;
    private String type;
    private Long id;
    private String email;
    private String fullName;
    private String role;

    public LoginResponse(String token, Long id, String email, String fullName, String role) {
        this.token = token;
        this.type = "Bearer";
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }
}