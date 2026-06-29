package org.demo.eventtracker.API.repository;

import org.demo.eventtracker.API.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

    Optional<AdminUser> findByEmailAndEnabledTrue(String email);
}