package com.example.eventsnow.repositories;


import com.example.eventsnow.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Events, Integer> {

}
