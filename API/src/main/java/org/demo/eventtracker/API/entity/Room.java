package org.demo.eventtracker.API.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String roomName;

    @Column(nullable = false)
    private Integer capacity;

    @OneToMany(mappedBy = "room")
    private List<Session> sessions;
}
