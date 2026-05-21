package org.demo.eventtracker.API.service;

import lombok.RequiredArgsConstructor;
import org.demo.eventtracker.API.dto.QuestionCreateRequest;
import org.demo.eventtracker.API.dto.QuestionResponse;
import org.demo.eventtracker.API.dto.QuestionUpvoteRequest;
import org.demo.eventtracker.API.entity.QuestionVote;
import org.demo.eventtracker.API.entity.Session;
import org.demo.eventtracker.API.entity.SessionQuestion;
import org.demo.eventtracker.API.repository.QuestionVoteRepository;
import org.demo.eventtracker.API.repository.SessionQuestionRepository;
import org.demo.eventtracker.API.repository.SessionRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final SessionRepository sessionRepository;
    private final SessionQuestionRepository sessionQuestionRepository;
    private final QuestionVoteRepository questionVoteRepository;

    @Transactional(readOnly = true)
    public List<QuestionResponse> getQuestions(Integer sessionId, String sort, String visitorId) {
        sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Session not found"
                ));

        List<SessionQuestion> questions = "recent".equalsIgnoreCase(sort)
                ? sessionQuestionRepository.findBySessionIdOrderByCreatedAtDesc(sessionId)
                : sessionQuestionRepository.findBySessionIdOrderByUpvoteCountDescCreatedAtAsc(sessionId);

        return questions.stream()
                .map(question -> {
                    boolean voted = hasVoted(question.getId(), visitorId);
                    return QuestionResponse.fromEntity(question, voted);
                })
                .toList();
    }

    @Transactional
    public QuestionResponse createQuestion(Integer sessionId, QuestionCreateRequest request) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Session not found"
                ));

        if (!isLive(session)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Questions can only be submitted during a live session"
            );
        }

        if (request.content() == null || request.content().trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Question content is required"
            );
        }

        SessionQuestion question = new SessionQuestion();
        question.setSession(session);
        question.setContent(request.content().trim());
        question.setAuthorName(formatAuthorName(request.authorName()));
        question.setUpvoteCount(0);
        question.setCreatedAt(Instant.now());

        SessionQuestion savedQuestion = sessionQuestionRepository.save(question);

        return QuestionResponse.fromEntity(savedQuestion, false);
    }

    @Transactional
    public QuestionResponse upvoteQuestion(Integer questionId, QuestionUpvoteRequest request) {
        if (request.visitorId() == null || request.visitorId().trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Visitor id is required"
            );
        }

        String visitorId = request.visitorId().trim();

        SessionQuestion question = sessionQuestionRepository.findById(questionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Question not found"
                ));

        /*if (!isLive(question.getSession())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Questions can only be upvoted during a live session"
            );
        }*/

        if (questionVoteRepository.existsByQuestionIdAndVisitorId(questionId, visitorId)) {
            return QuestionResponse.fromEntity(question, true);
        }

        QuestionVote vote = new QuestionVote();
        vote.setQuestion(question);
        vote.setVisitorId(visitorId);

        try {
            questionVoteRepository.save(vote);
            question.setUpvoteCount(question.getUpvoteCount() + 1);
            SessionQuestion savedQuestion = sessionQuestionRepository.save(question);

            return QuestionResponse.fromEntity(savedQuestion, true);
        } catch (DataIntegrityViolationException exception) {
            return QuestionResponse.fromEntity(question, true);
        }
    }

    private boolean hasVoted(Integer questionId, String visitorId) {
        if (visitorId == null || visitorId.trim().isEmpty()) {
            return false;
        }

        return questionVoteRepository.existsByQuestionIdAndVisitorId(
                questionId,
                visitorId.trim()
        );
    }

    private boolean isLive(Session session) {
        Instant now = Instant.now();

        return session.getStartTime() != null
                && session.getEndTime() != null
                && !now.isBefore(session.getStartTime())
                && !now.isAfter(session.getEndTime());
    }

    private String formatAuthorName(String authorName) {
        if (authorName == null || authorName.trim().isEmpty()) {
            return "Anonyme";
        }

        return authorName.trim();
    }
}