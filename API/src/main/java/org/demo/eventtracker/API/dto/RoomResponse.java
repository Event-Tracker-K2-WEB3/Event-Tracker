package org.demo.eventtracker.API.dto;

public class RoomResponse {
    private Integer id;
    private String name;

    public RoomResponse(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
}