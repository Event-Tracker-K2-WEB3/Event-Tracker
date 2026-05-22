package org.demo.eventtracker.API.repository;

import org.demo.eventtracker.API.entity.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpeakerRepository extends JpaRepository<Speaker, Integer> {
    List<Speaker> findAllByOrderByNameAsc();

    @Query(value = """
        SELECT COUNT(*)
        FROM session_speaker
        WHERE speaker_id = :speakerId
        """, nativeQuery = true)
    Integer countSessionsBySpeakerId(@Param("speakerId") Integer speakerId);

    @Query(value = """
    SELECT DISTINCT s.* 
    FROM speaker s
    JOIN session_speaker ss ON ss.speaker_id = s.id
    JOIN session sess ON sess.id = ss.session_id
    WHERE sess.event_id = :eventId
    """, nativeQuery = true)
    List<Speaker> findSpeakersByEventId(@Param("eventId") String eventId);

}