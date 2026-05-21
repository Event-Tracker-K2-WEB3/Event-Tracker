package org.demo.eventtracker.API.repository;

import org.demo.eventtracker.API.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, String> {

    Page<Event> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM event e WHERE " +
            "(:search IS NULL OR :search = '' OR " +
            "LOWER(e.title) LIKE LOWER(CONCAT('%', CAST(:search AS text), '%')) OR " +
            "LOWER(e.location) LIKE LOWER(CONCAT('%', CAST(:search AS text), '%')) OR " +
            "LOWER(e.description) LIKE LOWER(CONCAT('%', CAST(:search AS text), '%'))) AND " +
            "(:date IS NULL OR :date = '' OR :date = 'all' OR " +
            "(:date = 'today' AND DATE(e.start_date) = CURRENT_DATE) OR " +
            "(:date = 'week' AND DATE(e.start_date) BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '7 days') OR " +
            "(:date = 'month' AND DATE(e.start_date) BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '30 days')) AND " +
            "(:location IS NULL OR :location = '' OR " +
            "LOWER(e.location) LIKE LOWER(CONCAT('%', CAST(:location AS text), '%')))",
            countQuery = "SELECT COUNT(*) FROM event e WHERE " +
                    "(:search IS NULL OR :search = '' OR " +
                    "LOWER(e.title) LIKE LOWER(CONCAT('%', CAST(:search AS text), '%')) OR " +
                    "LOWER(e.location) LIKE LOWER(CONCAT('%', CAST(:search AS text), '%')) OR " +
                    "LOWER(e.description) LIKE LOWER(CONCAT('%', CAST(:search AS text), '%'))) AND " +
                    "(:date IS NULL OR :date = '' OR :date = 'all' OR " +
                    "(:date = 'today' AND DATE(e.start_date) = CURRENT_DATE) OR " +
                    "(:date = 'week' AND DATE(e.start_date) BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '7 days') OR " +
                    "(:date = 'month' AND DATE(e.start_date) BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '30 days')) AND " +
                    "(:location IS NULL OR :location = '' OR " +
                    "LOWER(e.location) LIKE LOWER(CONCAT('%', CAST(:location AS text), '%')))",
            nativeQuery = true)
    Page<Event> searchAndFilterByDateAndLocation(
            @Param("search") String search,
            @Param("date") String date,
            @Param("location") String location,
            Pageable pageable
    );
}