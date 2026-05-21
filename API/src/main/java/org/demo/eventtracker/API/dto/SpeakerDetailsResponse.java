package org.demo.eventtracker.API.dto;

import org.demo.eventtracker.API.entity.Speaker;

import java.util.List;

public record SpeakerDetailsResponse(
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
        Integer sessionCount,
        List<SpeakerSessionResponse> sessions
) {
    public static SpeakerDetailsResponse fromEntity(
            Speaker speaker,
            List<SpeakerSessionProjection> speakerSessions
    ) {
        List<SpeakerSessionResponse> sessions = speakerSessions == null
                ? List.of()
                : speakerSessions
                .stream()
                .map(SpeakerSessionResponse::fromProjection)
                .toList();

        return new SpeakerDetailsResponse(
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
                sessions.size(),
                sessions
        );
    }
}