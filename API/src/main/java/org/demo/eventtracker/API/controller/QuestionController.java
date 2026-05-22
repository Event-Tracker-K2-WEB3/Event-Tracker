package org.demo.eventtracker.API.controller;

import lombok.RequiredArgsConstructor;
import org.demo.eventtracker.API.dto.QuestionCreateRequest;
import org.demo.eventtracker.API.dto.QuestionResponse;
import org.demo.eventtracker.API.dto.QuestionUpvoteRequest;
import org.demo.eventtracker.API.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/sessions/{sessionId}/questions")
    public List<QuestionResponse> getQuestions(
            @PathVariable Integer sessionId,
            @RequestParam(defaultValue = "upvotes") String sort,
            @RequestParam(required = false) String visitorId
    ) {
        return questionService.getQuestions(sessionId, sort, visitorId);
    }

    @PostMapping("/sessions/{sessionId}/questions")
    public QuestionResponse createQuestion(
            @PathVariable Integer sessionId,
            @RequestBody QuestionCreateRequest request
    ) {
        return questionService.createQuestion(sessionId, request);
    }

    @PostMapping("/questions/{questionId}/upvote")
    public QuestionResponse upvoteQuestion(
            @PathVariable Integer questionId,
            @RequestBody QuestionUpvoteRequest request
    ) {
        return questionService.upvoteQuestion(questionId, request);
    }
}