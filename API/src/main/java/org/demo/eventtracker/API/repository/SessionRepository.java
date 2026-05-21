package org.demo.eventtracker.API.repository;

import org.demo.eventtracker.API.dto.SpeakerSessionProjection;
import org.demo.eventtracker.API.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Integer> {

    List<Session> findByRoomId(Integer roomId);

    List<Session> findByEventId(String eventId);

    @Query(value = """
            SELECT
                s.id AS id,
                s.title AS title,
                s.description AS description,
                s.type AS type,
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
}