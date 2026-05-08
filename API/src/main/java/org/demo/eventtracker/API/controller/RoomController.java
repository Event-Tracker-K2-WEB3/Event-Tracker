package org.demo.eventtracker.API.controller;

import org.demo.eventtracker.API.entity.Room;
import org.demo.eventtracker.API.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/rooms")
    public ResponseEntity<?> getAllRooms() {
        try {
            List<Room> rooms = roomRepository.findAll();
            return ResponseEntity.ok(rooms);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during the rooms recuperation : " + e.getMessage());
        }
    }

    @PostMapping("/rooms")
    public ResponseEntity<?> createRoom(@RequestBody Room room) {
        try {
            if (room.getRoomName() == null || room.getRoomName().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Rooms' name is required");
            }
            Room savedRoom = roomRepository.save(room);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during the room creation : " + e.getMessage());
        }
    }
}