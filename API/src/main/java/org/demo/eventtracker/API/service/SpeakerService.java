package org.demo.eventtracker.API.service;

import lombok.RequiredArgsConstructor;
import org.demo.eventtracker.API.dto.SpeakerDetailsResponse;
import org.demo.eventtracker.API.dto.SpeakerSessionProjection;
import org.demo.eventtracker.API.dto.SpeakerSummaryResponse;
import org.demo.eventtracker.API.entity.Speaker;
import org.demo.eventtracker.API.repository.SessionRepository;
import org.demo.eventtracker.API.repository.SpeakerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeakerService {
    private final SpeakerRepository speakerRepository;
    private final SessionRepository sessionRepository;

    @Transactional(readOnly = true)
    public List<SpeakerSummaryResponse> getAllSpeakers() {
        return speakerRepository.findAllByOrderByNameAsc()
                .stream()
                .map(SpeakerSummaryResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public SpeakerDetailsResponse getSpeakerById(Integer id) {
        Speaker speaker = speakerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Speaker not found"
                ));

        List<SpeakerSessionProjection> sessions =
                sessionRepository.findSpeakerSessionsBySpeakerId(id);

        return SpeakerDetailsResponse.fromEntity(speaker, sessions);
    }
}