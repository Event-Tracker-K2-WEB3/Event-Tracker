package org.demo.eventtracker.API.service;

import org.demo.eventtracker.API.dto.EventSessionResponse;
import org.demo.eventtracker.API.dto.SpeakerEventResponse;
import org.demo.eventtracker.API.entity.Event;
import org.demo.eventtracker.API.entity.Session;
import org.demo.eventtracker.API.entity.Speaker;
import org.demo.eventtracker.API.repository.EventRepository;
import org.demo.eventtracker.API.repository.SessionRepository;
import org.demo.eventtracker.API.repository.SpeakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SpeakerRepository speakerRepository;

    @Autowired
    private SessionRepository sessionRepository;

    public Event getEventById(String id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
    }

    public Event createEvent(Event event) {
        if (event.getTitle() == null || event.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (event.getStartDate() == null || event.getEndDate() == null) {
            throw new IllegalArgumentException("Start date and end date are required");
        }
        if (event.getStartDate().isAfter(event.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        return eventRepository.save(event);
    }

    public Page<Event> searchAndFilter(String search, String date, String location, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        if ((search == null || search.trim().isEmpty()) &&
                (date == null || date.trim().isEmpty() || date.equals("all")) &&
                (location == null || location.trim().isEmpty())
        ) {
            return eventRepository.findAll(pageable);
        }

        return eventRepository.searchAndFilterByDateAndLocation(search, date, location, pageable);
    }

    public List<SpeakerEventResponse> getSpeakersByEventId(String eventId) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found: " + eventId));

        List<Speaker> speakers = speakerRepository.findSpeakersByEventId(eventId);

        List<SpeakerEventResponse> response = new ArrayList<>();
        for (Speaker speaker : speakers) {
            response.add(new SpeakerEventResponse(
                    speaker.getId(),
                    speaker.getName(),
                    speaker.getRole(),
                    speaker.getPhoto(),
                    speaker.getInitials()
            ));
        }

        return response;
    }

    public List<EventSessionResponse> getSessionsByEventId(String eventId) {
        if (eventId == null || eventId.trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Event id is required"
            );
        }

        if (!eventRepository.existsById(eventId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Event not found"
            );
        }

        List<Session> sessions = sessionRepository.findByEventIdWithRoom(eventId);

        return sessions.stream()
                .map(EventSessionResponse::fromEntity)
                .toList();
    }

    public Event updateEvent(String id, Event eventDetails) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        existingEvent.setTitle(eventDetails.getTitle());
        existingEvent.setDescription(eventDetails.getDescription());
        existingEvent.setStartDate(eventDetails.getStartDate());
        existingEvent.setEndDate(eventDetails.getEndDate());
        existingEvent.setLocation(eventDetails.getLocation());

        return eventRepository.save(existingEvent);
    }

    public void deleteEvent(String id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        eventRepository.delete(event);
    }

}
