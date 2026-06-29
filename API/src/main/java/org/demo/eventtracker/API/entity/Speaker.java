package org.demo.eventtracker.API.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @Column(columnDefinition = "TEXT")
    private String photo;

    @Column(nullable = false)
    private String initials;

    private String linkedin;

    private String twitter;

    private String website;

    private String day;

    private String sessionType;

    @ManyToMany(mappedBy = "speakers", fetch = FetchType.LAZY)
    private List<Session> sessions = new ArrayList<>();
}