package org.demo.eventtracker.API.dto;

public record SpeakerSessionResponse(
        Integer id,
        String title,
        String description,
        String type,
        String startTime,
        String endTime,
        Integer capacity,
        String eventId,
        String eventTitle,
        Integer roomId,
        String roomName
) {
    public static SpeakerSessionResponse fromProjection(SpeakerSessionProjection session) {
        return new SpeakerSessionResponse(
                session.getId(),
                session.getTitle(),
                session.getDescription(),
                session.getType(),
                session.getStartTime() == null ? null : session.getStartTime().toString(),
                session.getEndTime() == null ? null : session.getEndTime().toString(),
                session.getCapacity(),
                session.getEventId(),
                session.getEventTitle(),
                session.getRoomId(),
                session.getRoomName()
        );
    }
}