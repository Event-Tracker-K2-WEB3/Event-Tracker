package org.demo.eventtracker.API.dto;

import java.time.Instant;

public record SessionCreateRequest(
        String title,
        String description,
        Instant startTime,
        Instant endTime,
        String type,
        Integer capacity,
        String eventId,
        Integer roomId,
        String image
) {
}