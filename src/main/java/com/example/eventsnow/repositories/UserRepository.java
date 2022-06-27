package com.example.eventsnow.repositories;

import com.example.eventsnow.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Users findByLogin(String login);
    List<Users> getUsersByOrganizerId(int organizerId);

}