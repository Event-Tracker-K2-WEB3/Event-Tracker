package org.demo.eventtracker.API.repository;

import org.demo.eventtracker.API.entity.QuestionVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Integer> {
    boolean existsByQuestionIdAndVisitorId(Integer questionId, String visitorId);
}