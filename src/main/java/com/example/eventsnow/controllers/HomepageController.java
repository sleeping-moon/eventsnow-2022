package com.example.eventsnow.controllers;

import com.example.eventsnow.dao.EventDaoJDBC;
import com.example.eventsnow.model.Events;
import com.example.eventsnow.model.Users;
import com.example.eventsnow.repositories.EventRepository;
import com.example.eventsnow.repositories.OrganizerRepository;
import com.example.eventsnow.repositories.TypeRepository;
import com.example.eventsnow.repositories.UserRepository;
import com.example.eventsnow.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/homepage")
public class HomepageController {


    private final UserRepository userRepository;
    private final EventDaoJDBC eventDaoJDBC;


    public HomepageController(UserRepository userRepository,EventDaoJDBC eventDaoJDBC) {
        this.userRepository = userRepository;
        this.eventDaoJDBC = eventDaoJDBC;
    }


    @GetMapping
    private String printMostAppropriateEvents(Model model, Principal principal) {
        Users thisUser = userRepository.findByLogin(principal.getName());
        List<Events> userEvents = eventDaoJDBC.userEvents(thisUser.getUserId());
        List<Events> mostPopularEvents = eventDaoJDBC.mostPopularEvents();
        for(int i =0; i< userEvents.size() && userEvents.size() >0 ; i++ ){
            if(thisUser.findUserEvents(userEvents.get(i))) userEvents.remove(userEvents.get(i));
        }
        if(userEvents.size()==0){
            userEvents.addAll(mostPopularEvents);
            for(int i =0; i< userEvents.size() && userEvents.size() >0 ; i++ ){
                if(thisUser.findUserEvents(userEvents.get(i))) userEvents.remove(userEvents.get(i));
            }
        }
        model.addAttribute("mostPopularEvents", mostPopularEvents);
        model.addAttribute("events", userEvents);
        model.addAttribute("userrole", thisUser.getUserRole().getId());
        return "homepage";
    }

    @GetMapping("/myevents")
    private String printMyEvents(Model model, Principal principal) {
        Users thisUser = userRepository.findByLogin(principal.getName());
        model.addAttribute("events",eventDaoJDBC.findActiveUserEvents(thisUser.getUserId()));
        model.addAttribute("userrole", thisUser.getUserRole().getId());
        return "myevents";
    }

    @GetMapping("/myevents/all")
    private String printAllMyEvents(Model model, Principal principal) {
        Users thisUser = userRepository.findByLogin(principal.getName());
        model.addAttribute("events",thisUser.getUserEvents());
        model.addAttribute("userrole", thisUser.getUserRole().getId());
        return "myevents";
    }

    @GetMapping("/myevents/past")
    private String printPastMyEvents(Model model, Principal principal) {
        Users thisUser = userRepository.findByLogin(principal.getName());
        model.addAttribute("events",eventDaoJDBC.findPastUserEvents(thisUser.getUserId()));
        model.addAttribute("userrole", thisUser.getUserRole().getId());
        return "myevents";
    }


}
