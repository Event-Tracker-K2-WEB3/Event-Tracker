package org.demo.eventtracker.API.dto;

import org.demo.eventtracker.API.entity.Room;
import org.demo.eventtracker.API.entity.Session;

import java.time.Instant;

public class EventSessionResponse {
    private final Integer id;
    private final String title;
    private final String description;
    private final Instant startTime;
    private final Instant endTime;
    private final Integer roomId;
    private final String roomName;

    public EventSessionResponse(
            Integer id,
            String title,
            String description,
            Instant startTime,
            Instant endTime,
            Integer roomId,
            String roomName
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public static EventSessionResponse fromEntity(Session session) {
        Room room = session.getRoom();

        return new EventSessionResponse(
                session.getId(),
                session.getTitle(),
                session.getDescription(),
                session.getStartTime(),
                session.getEndTime(),
                room != null ? room.getId() : null,
                room != null ? room.getName() : null
        );
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }
}