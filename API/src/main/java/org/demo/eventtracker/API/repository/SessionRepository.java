package org.demo.eventtracker.API.repository;

import org.demo.eventtracker.API.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    List<Session> findByRoomId(Integer roomId);
    List<Session> findByEventId(String eventId);
}