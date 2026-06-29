package org.demo.eventtracker.API.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "session_question")
public class SessionQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private String authorName;

    @Column(nullable = false)
    private Integer upvoteCount = 0;

    @Column(nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @PrePersist
    public void beforeCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }

        if (upvoteCount == null) {
            upvoteCount = 0;
        }
    }
}