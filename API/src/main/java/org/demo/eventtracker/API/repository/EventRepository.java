package org.demo.eventtracker.API.repository;

import org.demo.eventtracker.API.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventRepository extends JpaRepository<Event, String> {
    Page<Event> findAll(Pageable pageable);
}
