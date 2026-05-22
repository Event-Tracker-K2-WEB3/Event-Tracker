package org.demo.eventtracker.API.service;

import org.demo.eventtracker.API.dto.SpeakerEventResponse;
import org.demo.eventtracker.API.entity.Event;
import org.demo.eventtracker.API.entity.Speaker;
import org.demo.eventtracker.API.repository.EventRepository;
import org.demo.eventtracker.API.repository.SpeakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SpeakerRepository speakerRepository;

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
                    speaker.getPhoto()
            ));
        }

        return response;
    }

}
