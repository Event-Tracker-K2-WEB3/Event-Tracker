package org.demo.eventtracker.API.repository;

import org.demo.eventtracker.API.entity.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpeakerRepository extends JpaRepository<Speaker, Integer> {
    List<Speaker> findAllByOrderByNameAsc();
}