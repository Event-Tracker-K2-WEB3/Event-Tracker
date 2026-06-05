package org.demo.eventtracker.API.service;

import lombok.RequiredArgsConstructor;
import org.demo.eventtracker.API.dto.SessionDetailsResponse;
import org.demo.eventtracker.API.entity.Session;
import org.demo.eventtracker.API.repository.SessionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.demo.eventtracker.API.dto.SessionCreateRequest;
import org.demo.eventtracker.API.entity.Event;
import org.demo.eventtracker.API.entity.Room;
import org.demo.eventtracker.API.entity.Speaker;
import org.demo.eventtracker.API.repository.EventRepository;
import org.demo.eventtracker.API.repository.RoomRepository;
import org.demo.eventtracker.API.repository.SpeakerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final EventRepository eventRepository;
    private final RoomRepository roomRepository;
    private final SpeakerRepository speakerRepository;

    @Transactional(readOnly = true)
    public SessionDetailsResponse getSessionById(Integer id) {
        Session session = sessionRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Session not found"
                ));

        return SessionDetailsResponse.fromEntity(session);
    }

    public SessionDetailsResponse createSession(SessionCreateRequest request) {
        if (request.title() == null || request.title().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Session title is required");
        }

        if (request.startTime() == null || request.endTime() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Session start time and end time are required");
        }

        if (request.startTime().isAfter(request.endTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Session start time must be before end time");
        }

        if (request.type() == null || request.type().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Session type is required");
        }

        if (request.eventId() == null || request.eventId().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event id is required");
        }

        Event event = eventRepository.findById(request.eventId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Event not found"
                ));

        Room room = null;

        if (request.roomId() != null) {
            room = roomRepository.findById(request.roomId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Room not found"
                    ));
        }

        Session session = new Session();
        session.setTitle(request.title().trim());
        session.setDescription(request.description());
        session.setStartTime(request.startTime());
        session.setEndTime(request.endTime());
        session.setType(request.type().trim());
        session.setCapacity(request.capacity());
        session.setImage(request.image());
        session.setEvent(event);
        session.setRoom(room);
        session.setSpeakers(new ArrayList<>());

        Session savedSession = sessionRepository.save(session);

        Session sessionWithDetails = sessionRepository.findByIdWithDetails(savedSession.getId())
                .orElse(savedSession);

        return SessionDetailsResponse.fromEntity(sessionWithDetails);
    }

    public SessionDetailsResponse addSpeakerToSession(Integer sessionId, Integer speakerId) {
        Session session = sessionRepository.findByIdWithDetails(sessionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Session not found"
                ));

        Speaker speaker = speakerRepository.findById(speakerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Speaker not found"
                ));

        boolean alreadyAssigned = session.getSpeakers()
                .stream()
                .anyMatch(existingSpeaker -> existingSpeaker.getId().equals(speakerId));

        if (!alreadyAssigned) {
            session.getSpeakers().add(speaker);
            sessionRepository.save(session);
        }

        Session updatedSession = sessionRepository.findByIdWithDetails(sessionId)
                .orElse(session);

        return SessionDetailsResponse.fromEntity(updatedSession);
    }

    @Transactional(readOnly = true)
    public List<SessionDetailsResponse> getAllSessions() {
        return sessionRepository.findAllWithDetails()
                .stream()
                .map(SessionDetailsResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SessionDetailsResponse> getSessionsByRoom(Integer roomId) {
        return sessionRepository.findByRoomIdWithDetails(roomId)
                .stream()
                .map(SessionDetailsResponse::fromEntity)
                .toList();
    }
}