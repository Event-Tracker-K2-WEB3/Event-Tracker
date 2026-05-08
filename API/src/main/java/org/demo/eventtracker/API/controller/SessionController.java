package org.demo.eventtracker.API.controller;

import org.demo.eventtracker.API.entity.Session;
import org.demo.eventtracker.API.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SessionController {

    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping("/sessions")
    public ResponseEntity<?> getAllSessions() {
        try {
            List<Session> sessions = sessionRepository.findAll();
            return ResponseEntity.ok(sessions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/sessions")
    public ResponseEntity<?> createSession(@RequestBody Session newSession) {
        try {

            if (newSession.getStartTime().isAfter(newSession.getEndTime())) {
                return ResponseEntity.badRequest().body("Start time must be before end time.");
            }

            if (newSession.getRoom() != null) {
                List<Session> existingSessions = sessionRepository.findByRoomId(newSession.getRoom().getId());

                boolean isOverlapping = existingSessions.stream().anyMatch(existing ->
                        newSession.getStartTime().isBefore(existing.getEndTime()) &&
                                newSession.getEndTime().isAfter(existing.getStartTime())
                );

                if (isOverlapping) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Room already booked.");
                }
            }

            Session saved = sessionRepository.save(newSession);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/events/{eventId}/sessions")
    public ResponseEntity<?> getSessionsByEvent(@PathVariable String eventId) {
        try {

            return ResponseEntity.ok(sessionRepository.findByEventId(eventId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}