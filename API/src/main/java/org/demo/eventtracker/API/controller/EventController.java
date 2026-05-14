package org.demo.eventtracker.API.controller;

import org.demo.eventtracker.API.entity.Event;
import org.demo.eventtracker.API.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    EventRepository eventRepository;

    @GetMapping
    public ResponseEntity<?> getAllEvents() {
        try {
            List<Event> events = eventRepository.findAll();
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving events: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable String id) {
        try {
            Event event = eventRepository.findById(id).orElse(null);

            if (event == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Event not found with id: " + id);
            }

            return ResponseEntity.ok(event);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving event: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        try {
            if (event.getTitle() == null || event.getTitle().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Title is required");
            }

            if (event.getStartDate() == null || event.getEndDate() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Start date and end date are required");
            }

            if (event.getStartDate().isAfter(event.getEndDate())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Start date must be before end date");
            }

            Event savedEvent = eventRepository.save(event);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating event: " + e.getMessage());
        }
    }


}
