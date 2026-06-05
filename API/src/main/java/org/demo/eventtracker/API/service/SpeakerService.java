package org.demo.eventtracker.API.service;

import lombok.RequiredArgsConstructor;
import org.demo.eventtracker.API.dto.SpeakerCreateRequest;
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
        return speakerRepository.findAll()
                .stream()
                .map(speaker -> {
                    Integer sessionCount = speakerRepository.countSessionsBySpeakerId(speaker.getId());
                    return SpeakerSummaryResponse.fromEntity(speaker, sessionCount);
                })
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

    @Transactional
    public SpeakerSummaryResponse createSpeaker(SpeakerCreateRequest request) {
        if (request.name() == null || request.name().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Speaker name is required");
        }

        if (request.role() == null || request.role().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Speaker role is required");
        }

        if (request.specialty() == null || request.specialty().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Speaker specialty is required");
        }

        if (request.company() == null || request.company().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Speaker company is required");
        }

        Speaker speaker = new Speaker();
        speaker.setName(request.name().trim());
        speaker.setRole(request.role().trim());
        speaker.setSpecialty(request.specialty().trim());
        speaker.setCompany(request.company().trim());
        speaker.setBio(request.bio());
        speaker.setPhoto(request.photo());
        speaker.setInitials(
                request.initials() == null || request.initials().trim().isEmpty()
                        ? generateInitials(request.name())
                        : request.initials().trim()
        );
        speaker.setLinkedin(request.linkedin());
        speaker.setTwitter(request.twitter());
        speaker.setWebsite(request.website());
        speaker.setDay(request.day());
        speaker.setSessionType(request.sessionType());

        Speaker savedSpeaker = speakerRepository.save(speaker);

        return SpeakerSummaryResponse.fromEntity(savedSpeaker, 0);
    }

    @Transactional
    public SpeakerSummaryResponse updateSpeaker(Integer id, SpeakerCreateRequest request) {
        Speaker speaker = speakerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Speaker not found"
                ));

        if (request.name() == null || request.name().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Speaker name is required");
        }

        if (request.role() == null || request.role().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Speaker role is required");
        }

        if (request.specialty() == null || request.specialty().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Speaker specialty is required");
        }

        if (request.company() == null || request.company().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Speaker company is required");
        }

        speaker.setName(request.name().trim());
        speaker.setRole(request.role().trim());
        speaker.setSpecialty(request.specialty().trim());
        speaker.setCompany(request.company().trim());
        speaker.setBio(request.bio());
        speaker.setPhoto(request.photo());
        speaker.setInitials(
                request.initials() == null || request.initials().trim().isEmpty()
                        ? generateInitials(request.name())
                        : request.initials().trim()
        );
        speaker.setLinkedin(request.linkedin());
        speaker.setTwitter(request.twitter());
        speaker.setWebsite(request.website());
        speaker.setDay(request.day());
        speaker.setSessionType(request.sessionType());

        Speaker updatedSpeaker = speakerRepository.save(speaker);
        Integer sessionCount = speakerRepository.countSessionsBySpeakerId(updatedSpeaker.getId());

        return SpeakerSummaryResponse.fromEntity(updatedSpeaker, sessionCount);
    }

    @Transactional
    public void deleteSpeaker(Integer id) {
        Speaker speaker = speakerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Speaker not found"
                ));

        speakerRepository.delete(speaker);
    }

    private String generateInitials(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "?";
        }

        String[] parts = name.trim().split("\\s+");

        if (parts.length == 1) {
            return parts[0].substring(0, 1).toUpperCase();
        }

        return (parts[0].substring(0, 1) + parts[1].substring(0, 1)).toUpperCase();
    }
}