package org.demo.eventtracker.API.dto;

import org.demo.eventtracker.API.entity.SessionQuestion;

public record QuestionResponse(
        Integer id,
        String content,
        String authorName,
        Integer upvoteCount,
        String createdAt,
        Integer sessionId,
        Boolean votedByCurrentVisitor
) {
    public static QuestionResponse fromEntity(
            SessionQuestion question,
            boolean votedByCurrentVisitor
    ) {
        Integer sessionId = question.getSession() == null
                ? null
                : question.getSession().getId();

        return new QuestionResponse(
                question.getId(),
                question.getContent(),
                question.getAuthorName(),
                question.getUpvoteCount(),
                question.getCreatedAt() == null ? null : question.getCreatedAt().toString(),
                sessionId,
                votedByCurrentVisitor
        );
    }
}