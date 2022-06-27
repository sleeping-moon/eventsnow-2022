package com.example.eventsnow.services;

import com.example.eventsnow.model.Events;
import com.example.eventsnow.repositories.EventRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class EventService {
    @PersistenceContext
    private EntityManager entityManager;

    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void saveEvent() {
        entityManager.getTransaction().begin();
        Events event = new Events();
        entityManager.persist(event);
        entityManager.getTransaction().commit();
    }

    public Events getEvent(int id) {
        return entityManager.find(Events.class, id);
    }

    public Events save(Events event){
        return eventRepository.save(event);
    }
}
