package org.demo.eventtracker.API.dto;

import org.demo.eventtracker.API.entity.Speaker;

public record SpeakerSummaryResponse(
        Integer id,
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
        String sessionType,
        Integer sessionCount
) {
    public static SpeakerSummaryResponse fromEntity(Speaker speaker, Integer sessionCount) {
        return new SpeakerSummaryResponse(
                speaker.getId(),
                speaker.getName(),
                speaker.getRole(),
                speaker.getSpecialty(),
                speaker.getCompany(),
                speaker.getBio(),
                speaker.getPhoto(),
                speaker.getInitials(),
                speaker.getLinkedin(),
                speaker.getTwitter(),
                speaker.getWebsite(),
                speaker.getDay(),
                speaker.getSessionType(),
                sessionCount == null ? 0 : sessionCount
        );
    }
}