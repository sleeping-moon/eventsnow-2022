package com.example.eventsnow.controllers;

import com.example.eventsnow.dao.EventDaoJDBC;
import com.example.eventsnow.model.Events;
import com.example.eventsnow.model.Organizers;
import com.example.eventsnow.model.Types;
import com.example.eventsnow.model.Users;
import com.example.eventsnow.repositories.EventRepository;
import com.example.eventsnow.repositories.OrganizerRepository;
import com.example.eventsnow.repositories.TypeRepository;
import com.example.eventsnow.repositories.UserRepository;
import com.example.eventsnow.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/allevents")
public class EventController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);
    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;
    private final UserRepository userRepository;
    private final TypeRepository typesRepository;
    private final UserService userService;
    private final EventDaoJDBC eventDaoJDBC;


    public EventController(EventRepository eventRepository, OrganizerRepository organizerRepository,
                           UserRepository userRepository, UserService userService,
                           TypeRepository typesRepository, EventDaoJDBC eventDaoJDBC) {
        this.eventRepository = eventRepository;
        this.organizerRepository = organizerRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.typesRepository = typesRepository;
        this.eventDaoJDBC = eventDaoJDBC;
    }

    @GetMapping
    private String printAllEvents(Model model, Principal principal) {
        List<Events> events = eventDaoJDBC.findActiveEvents();
        List<Types> type = typesRepository.findAll();
        List<Types> selectedtypes =new ArrayList<Types>();
        model.addAttribute("date",new String());
        model.addAttribute("types",selectedtypes);
        model.addAttribute("events",events);
        model.addAttribute("type",type);
        return "allevents";
    }

    @PostMapping
    private String printSelectedEvents(@ModelAttribute("types") ArrayList<Types> types,
                                       @ModelAttribute("date") String date,
                                       @ModelAttribute("events") ArrayList<Events> events,
                                       Model model) {
        List<Types> type = typesRepository.findAll();
        List<Types> selectedtypes =new ArrayList<Types>();
        if(!date.isEmpty() && types.size() != 0){
            String where= "";
            for(Types thistype : types){
                where+=" or type_id ="+ thistype.getTypeId();
            }
            where= where.substring(3);
            try {
                events = (ArrayList<Events>)eventDaoJDBC.findActiveEventsByTypeAndDate(where,new SimpleDateFormat("yyyy-MM-dd").parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if(!date.isEmpty()){
            try {
                events = (ArrayList<Events>)eventDaoJDBC.findActiveEventsByDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if(types.size() != 0){
            String where= "";
            for(Types thistype : types){
                where+=" or type_id ="+ thistype.getTypeId();
            }
            where= where.substring(3);
            events = (ArrayList<Events>)eventDaoJDBC.findActiveEventsByType(where);
        }else{
            events = (ArrayList<Events>)eventDaoJDBC.findActiveEvents();
        }
        model.addAttribute("date",new String());
        model.addAttribute("types",selectedtypes);
        model.addAttribute("events",events);
        model.addAttribute("type",type);
        return "allevents";
    }

    @GetMapping("/event/{id}")
    private String printEvent(@PathVariable("id") int id, Model model, Principal principal) {
        Events event = eventRepository.getById(id);
        Users thisUser = userRepository.findByLogin(principal.getName());
        model.addAttribute("event", event);
        model.addAttribute("user", thisUser);
        return "event";
    }


    @PostMapping("/event/{id}/gotoevent")
    private String gotoevent(@PathVariable("id") int id, Principal principal) {
        Events event = eventRepository.getById(id);
        Users thisUser = userRepository.findByLogin(principal.getName());
        if (event.getUsers().size() < event.getNumberOfPlaces()) {
            thisUser.addUserEvents(event);
            userService.updateUser(thisUser);
            LOGGER.info(thisUser.getLogin()+" (Id:"+thisUser.getUserId()+") go to event "+
                    event.getTitle()+"(Id:"+event.getEventId()+")");
        }
        return "redirect:/allevents";
    }

    @GetMapping("/{id}/organizationview")
    private String organizationView(@PathVariable("id") int id,
                                    Model model) {
            Organizers organization = organizerRepository.getById(id);
            List<Events> events = organization.getOrganizersEvents();
            if (organization.getIsActive() == 1) {
                model.addAttribute("organization", organization);
                model.addAttribute("event", events);
                return "organizationview";
            }
           return "unavalibleorganization";
        }

}
