package org.demo.eventtracker.API.service;

import org.demo.eventtracker.API.entity.Event;
import org.demo.eventtracker.API.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;


    public Page<Event> getEventsPage(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return eventRepository.findAll(pageable);
    }
}
