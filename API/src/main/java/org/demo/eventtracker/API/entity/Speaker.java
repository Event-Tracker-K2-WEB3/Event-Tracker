package org.demo.eventtracker.API.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Speaker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String specialty;

    @Column(nullable = false)
    private String company;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column
    private String photo;

    @Column(nullable = false)
    private String initials;

    @Column
    private String linkedin;

    @Column
    private String twitter;

    @Column
    private String website;

    @Column
    private String day;

    @Column
    private String sessionType;

    @Column
    private Integer sessions;
}
