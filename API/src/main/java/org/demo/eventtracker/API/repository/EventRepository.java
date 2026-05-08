package org.demo.eventtracker.API.repository;

import org.demo.eventtracker.API.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {

}
