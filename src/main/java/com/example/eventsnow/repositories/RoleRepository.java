package com.example.eventsnow.repositories;

import com.example.eventsnow.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Integer> {
}
