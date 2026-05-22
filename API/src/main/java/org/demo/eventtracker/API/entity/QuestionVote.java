package org.demo.eventtracker.API.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(
        name = "question_vote",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_question_vote_visitor",
                        columnNames = {"question_id", "visitor_id"}
                )
        }
)
public class QuestionVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "visitor_id", nullable = false)
    private String visitorId;

    @Column(nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private SessionQuestion question;

    @PrePersist
    public void beforeCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}