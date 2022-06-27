package com.example.eventsnow.dao;


import com.example.eventsnow.model.Events;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class EventDaoJDBC {
    private final JdbcTemplate jdbcTemplate;

    public EventDaoJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Events> mostPopularEvents() {
        return jdbcTemplate.query("select e.title , e.poster ,e.second_poster, e.description, e.event_id " +
                "from users_events ue  " +
                "join events e on ue.event_id = e.event_id  " +
                "where e.date_of_event > CURRENT_TIMESTAMP " +
                "group by e.title , e.poster, e.description, e.event_id " +
                "order by count(e.title) desc limit 3", new BeanPropertyRowMapper<>(Events.class));
    }

    public List<Events> userEvents(Integer id) {
        return jdbcTemplate.query("select e.title, e.poster,e.second_poster, e.description, e.event_id , count(e.title) as counttitle " +
                "from types_users tu  " +
                "join types_events te on te.type_id = tu.type_id  " +
                "join events e  on e.event_id  = te.event_id  " +
                "where e.date_of_event > CURRENT_TIMESTAMP and tu.user_id = ? " +
                "group by e.title , e.poster , e.description, e.event_id " +
                "order by counttitle  desc limit 12 ", new BeanPropertyRowMapper<>(Events.class), id);
    }

    public List<Events> findActiveEvents() {
        return jdbcTemplate.query("select title, poster, second_poster, description, event_id, date_of_event   " +
                "from events  " +
                "where date_of_event > CURRENT_TIMESTAMP and is_active = 1", new BeanPropertyRowMapper<>(Events.class));
    }

    public List<Events> findActiveEventsByDate(Date date) {
        return jdbcTemplate.query("select title, poster,second_poster, description, event_id, date_of_event  \n" +
                "from events \n" +
                "where date_of_event > CURRENT_TIMESTAMP and is_active = 1 and DATE(date_of_event) = ?", new BeanPropertyRowMapper<>(Events.class),date);
    }

    public List<Events> findActiveEventsByType(String where){
        return jdbcTemplate.query("select e.title, e.poster,e.second_poster, e.description, e.event_id, e.date_of_event\n" +
                "from types_events te \n" +
                "join events e on te.event_id = e.event_id\n " +
                "where "+ where + " and e.date_of_event > CURRENT_TIMESTAMP and e.is_active = 1 " +
                "group by e.title , e.poster , e.description, e.event_id, e.date_of_event", new BeanPropertyRowMapper<>(Events.class));

    }

    public List<Events> findActiveEventsByTypeAndDate(String where,Date date){
        return jdbcTemplate.query("select e.title, e.poster,e.second_poster, e.description, e.event_id, e.date_of_event\n" +
                "from types_events te \n" +
                "join events e on te.event_id = e.event_id\n" +
                "where "+ where + " and e.date_of_event > CURRENT_TIMESTAMP and e.is_active = 1 and DATE(e.date_of_event) = ?\n" +
                "group by e.title , e.poster , e.description, e.event_id, e.date_of_event", new BeanPropertyRowMapper<>(Events.class),date);

    }

    public List<Events> findPastUserEvents(int id) {
        return jdbcTemplate.query("select e.title, e.poster,e.second_poster, e.description, e.event_id, e.date_of_event " +
                "from users_events ue " +
                "join events e  on e.event_id  = ue.event_id " +
                "where e.date_of_event < CURRENT_TIMESTAMP and ue.user_id = ? " +
                "group by e.title , e.poster , e.description, e.event_id, e.date_of_event " +
                "order by  count(e.title)  desc ", new BeanPropertyRowMapper<>(Events.class), id);
    }

    public List<Events> findActiveUserEvents(int id) {
        return jdbcTemplate.query("select e.title, e.poster,e.second_poster, e.description, e.event_id, e.date_of_event " +
                "from users_events ue " +
                "join events e  on e.event_id  = ue.event_id " +
                "where e.date_of_event > CURRENT_TIMESTAMP and ue.user_id = ? " +
                "group by e.title , e.poster , e.description, e.event_id, e.date_of_event " +
                "order by  count(e.title)  desc ", new BeanPropertyRowMapper<>(Events.class), id);
    }


}
