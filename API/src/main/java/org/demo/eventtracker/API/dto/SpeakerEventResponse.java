package org.demo.eventtracker.API.dto;

public class SpeakerEventResponse {
    private Integer id;
    private String name;
    private String role;
    private String photo;
    private String initials;

    public SpeakerEventResponse(Integer id, String name, String role, String photo, String initials) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.photo = photo;
        this.initials = initials;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getPhoto() { return photo; }
    public String getInitials() {return  initials; }
}