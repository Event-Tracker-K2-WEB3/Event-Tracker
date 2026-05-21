package org.demo.eventtracker.API.service;

import lombok.RequiredArgsConstructor;
import org.demo.eventtracker.API.dto.SessionDetailsResponse;
import org.demo.eventtracker.API.entity.Session;
import org.demo.eventtracker.API.repository.SessionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;

    @Transactional(readOnly = true)
    public SessionDetailsResponse getSessionById(Integer id) {
        Session session = sessionRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Session not found"
                ));

        return SessionDetailsResponse.fromEntity(session);
    }
}