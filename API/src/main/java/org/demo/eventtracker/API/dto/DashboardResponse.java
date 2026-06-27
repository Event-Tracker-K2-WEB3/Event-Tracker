package org.demo.eventtracker.API.dto;

import java.time.Instant;
import java.util.List;

public record DashboardResponse(
        long totalEvents,
        long totalSessions,
        long totalSpeakers,
        long totalRooms,
        long liveSessions,
        List<DashboardChartPoint> sessionsByDay,
        List<DashboardSessionSummary> upcomingSessions,
        List<DashboardSessionSummary> latestSessions,
        List<DashboardSpeakerSummary> latestSpeakers
) {
    public record DashboardChartPoint(
            String date,
            String label,
            long sessions
    ) {
    }

    public record DashboardSessionSummary(
            Integer id,
            String title,
            String type,
            Instant startTime,
            Instant endTime,
            String eventId,
            String eventTitle,
            Integer roomId,
            String roomName,
            Integer capacity,
            boolean live
    ) {
    }

    public record DashboardSpeakerSummary(
            Integer id,
            String name,
            String role,
            String company,
            String specialty,
            String photo,
            String initials
    ) {
    }
}