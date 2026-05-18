package org.demo.eventtracker.API.controller;

import org.demo.eventtracker.API.entity.Speaker;
import org.demo.eventtracker.API.repository.SpeakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SpeakerController {

    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping("/speakers")
    public ResponseEntity<?> getAllSpeakers() {
        try {
            List<Speaker> speakers = speakerRepository.findAll();
            return ResponseEntity.ok(speakers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving speakers: " + e.getMessage());
        }
    }

    @PostMapping("/speakers")
    public ResponseEntity<?> createSpeaker(@RequestBody Speaker speaker) {
        try {
            if (speaker.getName() == null || speaker.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Speaker name is required");
            }
            Speaker saved = speakerRepository.save(speaker);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating speaker: " + e.getMessage());
        }
    }

    @GetMapping("/speakers/{id}")
    public ResponseEntity<?> getSpeakerById(@PathVariable Integer id) {
        try {
            return speakerRepository.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}