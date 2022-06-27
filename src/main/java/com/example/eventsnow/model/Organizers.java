package com.example.eventsnow.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "organizers")
public class Organizers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int organizerId;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String email;
    @Column(name = "is_active")
    private int isActive;
    @ManyToMany
    @JoinTable(name = "organizers_events", joinColumns = @JoinColumn(name = "organizer_id"), inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Events> organizersEvents;

    public int getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(int organizerId) {
        this.organizerId = organizerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int active) {
        isActive = active;
    }

    public List<Events> getOrganizersEvents() {
        return organizersEvents;
    }

    public void setOrganizersEvents(List<Events> organizersEvents) {
        this.organizersEvents = organizersEvents;
    }
}
