package org.demo.eventtracker.API.repository;

import org.demo.eventtracker.API.entity.SessionQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionQuestionRepository extends JpaRepository<SessionQuestion, Integer> {
    List<SessionQuestion> findBySessionIdOrderByUpvoteCountDescCreatedAtAsc(Integer sessionId);

    List<SessionQuestion> findBySessionIdOrderByCreatedAtDesc(Integer sessionId);
}