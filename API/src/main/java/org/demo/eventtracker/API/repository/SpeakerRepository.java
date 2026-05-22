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
}