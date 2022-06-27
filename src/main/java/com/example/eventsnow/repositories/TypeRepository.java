package com.example.eventsnow.repositories;

import com.example.eventsnow.model.Events;
import com.example.eventsnow.model.Types;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TypeRepository extends JpaRepository<Types, Integer> {

}
