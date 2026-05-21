package org.demo.eventtracker.API.dto;

import java.time.Instant;

public interface SpeakerSessionProjection {
    Integer getId();

    String getTitle();

    String getDescription();

    String getType();

    String getImage();

    Instant getStartTime();

    Instant getEndTime();

    Integer getCapacity();

    String getEventId();

    String getEventTitle();

    Integer getRoomId();

    String getRoomName();
}