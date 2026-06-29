package org.demo.eventtracker.API.dto;

public record QuestionCreateRequest(
        String content,
        String authorName
) {
}