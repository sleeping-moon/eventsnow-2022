package com.example.eventsnow.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "types")
public class Types {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="type_id")
    private int typeId;
    @Column(name ="title")
    private  String title;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "types_events", joinColumns = @JoinColumn(name = "type_id"), inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Events> events;
    @ManyToMany
    @JoinTable(name = "types_users", joinColumns = @JoinColumn(name = "type_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Users> users;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Events> getEvents() {
        return events;
    }

    public void setEvents(List<Events> events) {
        this.events = events;
    }

    public void addEvents(Events events) {
        this.events.add(events);
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }
}
