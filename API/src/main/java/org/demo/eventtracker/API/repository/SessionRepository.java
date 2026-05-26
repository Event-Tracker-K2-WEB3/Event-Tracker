package org.demo.eventtracker.API.repository;

import org.demo.eventtracker.API.dto.SpeakerSessionProjection;
import org.demo.eventtracker.API.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Integer> {
    List<Session> findByRoomId(Integer roomId);

    List<Session> findByEventId(String eventId);

    @Query("""
            select distinct s
            from Session s
            left join fetch s.event
            left join fetch s.room
            left join fetch s.speakers
            where s.id = :id
            """)
    Optional<Session> findByIdWithDetails(Integer id);

    @Query(value = """
            SELECT
                s.id AS id,
                s.title AS title,
                s.description AS description,
                s.type AS type,
                s.image AS image,
                s.start_time AS "startTime",
                s.end_time AS "endTime",
                s.capacity AS capacity,
                s.event_id AS "eventId",
                e.title AS "eventTitle",
                s.room_id AS "roomId",
                r.name AS "roomName"
            FROM session_speaker ss
            JOIN session s ON s.id = ss.session_id
            LEFT JOIN event e ON e.id = s.event_id
            LEFT JOIN room r ON r.id = s.room_id
            WHERE ss.speaker_id = :speakerId
            ORDER BY s.start_time ASC
            """, nativeQuery = true)
    List<SpeakerSessionProjection> findSpeakerSessionsBySpeakerId(@Param("speakerId") Integer speakerId);

    @Query("""
        SELECT session
        FROM Session session
        LEFT JOIN FETCH session.room
        WHERE session.event.id = :eventId
        ORDER BY session.startTime ASC
        """)
    List<Session> findByEventIdWithRoom(@Param("eventId") String eventId);

}