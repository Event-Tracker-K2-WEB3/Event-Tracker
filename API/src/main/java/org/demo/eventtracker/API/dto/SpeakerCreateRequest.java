package org.demo.eventtracker.API.dto;

public record SpeakerCreateRequest(
        String name,
        String role,
        String specialty,
        String company,
        String bio,
        String photo,
        String initials,
        String linkedin,
        String twitter,
        String website,
        String day,
        String sessionType
) {
}