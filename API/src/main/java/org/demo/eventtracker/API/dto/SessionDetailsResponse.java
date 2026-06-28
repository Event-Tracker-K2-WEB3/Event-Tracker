package org.demo.eventtracker.API.dto;

import org.demo.eventtracker.API.entity.Session;

import java.time.Instant;
import java.util.List;

public record SessionDetailsResponse(
        Integer id,
        String title,
        String description,
        String type,
        String image,
        String startTime,
        String endTime,
        Integer capacity,
        String eventId,
        String eventTitle,
        Integer roomId,
        String roomName,
        Boolean live,
        Integer speakerCount,
        List<SessionSpeakerResponse> speakers
) {
    public static SessionDetailsResponse fromEntity(Session session) {
        String eventId = session.getEvent() == null ? null : session.getEvent().getId();
        String eventTitle = session.getEvent() == null ? null : session.getEvent().getTitle();

        Integer roomId = session.getRoom() == null ? null : session.getRoom().getId();
        String roomName = session.getRoom() == null ? null : session.getRoom().getName();

        Instant now = Instant.now();

        boolean live = session.getStartTime() != null
                && session.getEndTime() != null
                && !now.isBefore(session.getStartTime())
                && !now.isAfter(session.getEndTime());

        List<SessionSpeakerResponse> speakers = session.getSpeakers() == null
                ? List.of()
                : session.getSpeakers()
                .stream()
                .map(SessionSpeakerResponse::fromEntity)
                .toList();

        return new SessionDetailsResponse(
                session.getId(),
                session.getTitle(),
                session.getDescription(),
                session.getType(),
                session.getImage(),
                session.getStartTime() == null ? null : session.getStartTime().toString(),
                session.getEndTime() == null ? null : session.getEndTime().toString(),
                session.getCapacity(),
                eventId,
                eventTitle,
                roomId,
                roomName,
                live,
                speakers.size(),
                speakers
        );
    }
}