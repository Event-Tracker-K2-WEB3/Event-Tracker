package org.demo.eventtracker.API.controller;

import lombok.RequiredArgsConstructor;
import org.demo.eventtracker.API.dto.SpeakerDetailsResponse;
import org.demo.eventtracker.API.dto.SpeakerSummaryResponse;
import org.demo.eventtracker.API.service.SpeakerService;
import org.springframework.web.bind.annotation.*;
import org.demo.eventtracker.API.dto.SpeakerCreateRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/speakers")
public class SpeakerController {
    private final SpeakerService speakerService;

    @GetMapping
    public List<SpeakerSummaryResponse> getAllSpeakers() {
        return speakerService.getAllSpeakers();
    }

    @GetMapping("/{id}")
    public SpeakerDetailsResponse getSpeakerById(@PathVariable Integer id) {
        return speakerService.getSpeakerById(id);
    }

    @PostMapping
    public SpeakerSummaryResponse createSpeaker(@RequestBody SpeakerCreateRequest request) {
        return speakerService.createSpeaker(request);
    }

    @PutMapping("/{id}")
    public SpeakerSummaryResponse updateSpeaker(
            @PathVariable Integer id,
            @RequestBody SpeakerCreateRequest request
    ) {
        return speakerService.updateSpeaker(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteSpeaker(@PathVariable Integer id) {
        speakerService.deleteSpeaker(id);
    }
}