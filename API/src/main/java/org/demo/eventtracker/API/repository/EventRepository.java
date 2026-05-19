package org.demo.eventtracker.API.repository;

import org.demo.eventtracker.API.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface EventRepository extends JpaRepository<Event, String> {
    Page<Event> findAll(Pageable pageable);

    @Query("SELECT e FROM Event e WHERE " +
            "LOWER(e.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(e.location) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(e.description) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Event> searchEvents(@Param("search") String search, Pageable pageable);

}
