package org.demo.eventtracker.API.controller;

import org.demo.eventtracker.API.repository.EventRepository;
import org.demo.eventtracker.API.repository.SessionRepository;
import org.demo.eventtracker.API.repository.SpeakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/about")
@CrossOrigin(origins = "http://localhost:3000") // Permet à Next.js de parler au Backend
public class AboutController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SpeakerRepository speakerRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping("/about/stats")
    public ResponseEntity<Map<String, Long>> getAboutStats() {


        long countEvents = eventRepository.count();
        long countSpeakers = speakerRepository.count();
        long countSessions = sessionRepository.count();


        Map<String, Long> reponse = new HashMap<>();
        reponse.put("totalEvents", countEvents);
        reponse.put("totalSpeakers", countSpeakers);
        reponse.put("totalSessions", countSessions);


        return ResponseEntity.ok(reponse);
    }
}