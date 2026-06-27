package org.demo.eventtracker.API.service;

import lombok.RequiredArgsConstructor;
import org.demo.eventtracker.API.dto.DashboardResponse;
import org.demo.eventtracker.API.entity.Session;
import org.demo.eventtracker.API.entity.Speaker;
import org.demo.eventtracker.API.repository.EventRepository;
import org.demo.eventtracker.API.repository.RoomRepository;
import org.demo.eventtracker.API.repository.SessionRepository;
import org.demo.eventtracker.API.repository.SpeakerRepository;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final EventRepository eventRepository;
    private final SessionRepository sessionRepository;
    private final SpeakerRepository speakerRepository;
    private final RoomRepository roomRepository;

    public DashboardResponse getDashboardSummary() {
        Instant now = Instant.now();

        long totalEvents = eventRepository.count();
        long totalSessions = sessionRepository.count();
        long totalSpeakers = speakerRepository.count();
        long totalRooms = roomRepository.count();

        long liveSessions = sessionRepository
                .countByStartTimeLessThanEqualAndEndTimeGreaterThanEqual(now, now);

        List<DashboardResponse.DashboardChartPoint> sessionsByDay =
                buildSessionsByDay();

        List<DashboardResponse.DashboardSessionSummary> upcomingSessions =
                sessionRepository.findTop5ByStartTimeAfterOrderByStartTimeAsc(now)
                        .stream()
                        .map(session -> toSessionSummary(session, now))
                        .toList();

        List<DashboardResponse.DashboardSessionSummary> latestSessions =
                sessionRepository.findTop5ByOrderByIdDesc()
                        .stream()
                        .map(session -> toSessionSummary(session, now))
                        .toList();

        List<DashboardResponse.DashboardSpeakerSummary> latestSpeakers =
                speakerRepository.findTop5ByOrderByIdDesc()
                        .stream()
                        .map(this::toSpeakerSummary)
                        .toList();

        return new DashboardResponse(
                totalEvents,
                totalSessions,
                totalSpeakers,
                totalRooms,
                liveSessions,
                sessionsByDay,
                upcomingSessions,
                latestSessions,
                latestSpeakers
        );
    }

    private List<DashboardResponse.DashboardChartPoint> buildSessionsByDay() {
        ZoneId zoneId = ZoneId.systemDefault();

        LocalDate today = LocalDate.now(zoneId);
        LocalDate startDate = today.minusDays(6);
        LocalDate endDate = today;

        Instant startInstant = startDate
                .atStartOfDay(zoneId)
                .toInstant();

        Instant endInstant = endDate
                .plusDays(1)
                .atStartOfDay(zoneId)
                .toInstant();

        List<Session> sessions = sessionRepository
                .findByStartTimeBetweenOrderByStartTimeAsc(startInstant, endInstant);

        Map<LocalDate, Long> sessionCountByDate = sessions.stream()
                .collect(Collectors.groupingBy(
                        session -> LocalDateTime
                                .ofInstant(session.getStartTime(), zoneId)
                                .toLocalDate(),
                        Collectors.counting()
                ));

        DateTimeFormatter labelFormatter = DateTimeFormatter.ofPattern("MMM d", java.util.Locale.ENGLISH);

        return startDate
                .datesUntil(endDate.plusDays(1))
                .map(date -> new DashboardResponse.DashboardChartPoint(
                        date.toString(),
                        date.format(labelFormatter),
                        sessionCountByDate.getOrDefault(date, 0L)
                ))
                .toList();
    }

    private DashboardResponse.DashboardSessionSummary toSessionSummary(
            Session session,
            Instant now
    ) {
        boolean isLive = session.getStartTime() != null
                && session.getEndTime() != null
                && !session.getStartTime().isAfter(now)
                && !session.getEndTime().isBefore(now);

        return new DashboardResponse.DashboardSessionSummary(
                session.getId(),
                session.getTitle(),
                session.getType(),
                session.getStartTime(),
                session.getEndTime(),
                session.getEvent() != null ? session.getEvent().getId() : null,
                session.getEvent() != null ? session.getEvent().getTitle() : null,
                session.getRoom() != null ? session.getRoom().getId() : null,
                session.getRoom() != null ? session.getRoom().getName() : null,
                session.getCapacity(),
                isLive
        );
    }

    private DashboardResponse.DashboardSpeakerSummary toSpeakerSummary(
            Speaker speaker
    ) {
        return new DashboardResponse.DashboardSpeakerSummary(
                speaker.getId(),
                speaker.getName(),
                speaker.getRole(),
                speaker.getCompany(),
                speaker.getSpecialty(),
                speaker.getPhoto(),
                speaker.getInitials()
        );
    }
}