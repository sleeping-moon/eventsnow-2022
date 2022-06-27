package com.example.eventsnow.repositories;

import com.example.eventsnow.model.Organizers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizerRepository extends JpaRepository<Organizers, Integer> {
}
