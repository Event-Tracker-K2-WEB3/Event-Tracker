package org.demo.eventtracker.API.dto;

import org.demo.eventtracker.API.entity.Speaker;

public record SessionSpeakerResponse(
        Integer id,
        String name,
        String role,
        String specialty,
        String company,
        String photo,
        String initials
) {
    public static SessionSpeakerResponse fromEntity(Speaker speaker) {
        return new SessionSpeakerResponse(
                speaker.getId(),
                speaker.getName(),
                speaker.getRole(),
                speaker.getSpecialty(),
                speaker.getCompany(),
                speaker.getPhoto(),
                speaker.getInitials()
        );
    }
}