package com.example.eventsnow.model;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Roles implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="role_id")
    private int roleId;
    @Column(name="name")
    private String name;

    public Roles() {
    }

    public Roles(int id, String name) {
        this.roleId = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return "ROLE_"+getName().toUpperCase();
    }

    public void setId(int id) {
        this.roleId = id;
    }

    public int getId() {
        return roleId;
    }
}
