package org.demo.eventtracker.API.controller;

import lombok.RequiredArgsConstructor;
import org.demo.eventtracker.API.dto.SessionDetailsResponse;
import org.demo.eventtracker.API.entity.Session;
import org.demo.eventtracker.API.repository.SessionRepository;
import org.demo.eventtracker.API.service.SessionService;
import org.springframework.web.bind.annotation.*;
import org.demo.eventtracker.API.dto.SessionCreateRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessions")
public class SessionController {
    private final SessionRepository sessionRepository;
    private final SessionService sessionService;

    @GetMapping
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    @GetMapping("/{id}")
    public SessionDetailsResponse getSessionById(@PathVariable Integer id) {
        return sessionService.getSessionById(id);
    }

    @GetMapping("/room/{roomId}")
    public List<Session> getSessionsByRoom(@PathVariable Integer roomId) {
        return sessionRepository.findByRoomId(roomId);
    }

    @PostMapping
    public SessionDetailsResponse createSession(@RequestBody SessionCreateRequest request) {
        return sessionService.createSession(request);
    }

    @PostMapping("/{sessionId}/speakers/{speakerId}")
    public SessionDetailsResponse addSpeakerToSession(
            @PathVariable Integer sessionId,
            @PathVariable Integer speakerId
    ) {
        return sessionService.addSpeakerToSession(sessionId, speakerId);
    }
}