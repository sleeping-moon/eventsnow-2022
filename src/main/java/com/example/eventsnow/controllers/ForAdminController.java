package com.example.eventsnow.controllers;

import com.example.eventsnow.model.Events;
import com.example.eventsnow.model.Organizers;
import com.example.eventsnow.model.Types;
import com.example.eventsnow.model.Users;
import com.example.eventsnow.repositories.EventRepository;
import com.example.eventsnow.repositories.OrganizerRepository;
import com.example.eventsnow.repositories.TypeRepository;
import com.example.eventsnow.repositories.UserRepository;
import com.example.eventsnow.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/foradmin")
public class ForAdminController {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final TypeRepository typesRepository;
    private final OrganizerRepository organizerRepository;
    private final UserService userService;


    public ForAdminController(UserRepository userRepository, UserService userService,
                              TypeRepository typesRepository,OrganizerRepository organizerRepository,
                              EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.typesRepository = typesRepository;
        this.organizerRepository = organizerRepository;
        this.eventRepository = eventRepository;
    }

    @GetMapping
    private String printAll(Model model) {
        List<Users> users = userRepository.findAll();
        String userBan = "";
        List<Types> allTypes = typesRepository.findAll();
        List<Organizers> allOrganizer = organizerRepository.findAll();
        List<Events> allEvents = eventRepository.findAll();
        Types type = new Types();
        model.addAttribute("allEvents", allEvents);
        model.addAttribute("allOrganizer", allOrganizer);
        model.addAttribute("allTypes", allTypes);
        model.addAttribute("userBan", userBan);
        model.addAttribute("users", users);
        model.addAttribute("type", type);
        return "foradmin";
    }

    @PostMapping
    private String addType(@ModelAttribute("type") Types type) {
        typesRepository.save(type);
        return "redirect:/foradmin";
    }

    @GetMapping("/banuser/{id}")
    private String printAllUser(@PathVariable("id") int id, Model model) {
        Users user = userRepository.getById(id);
        if (user != null) {
            if (user.getIsActive() == 1) user.setIsActive(0);
            else user.setIsActive(1);
            userService.updateUser(user);
        }
        return "redirect:/foradmin";
    }

    @GetMapping("/banorganizer/{id}")
    private String banOrganizer(@PathVariable("id") int id, Model model) {
        Organizers organizers = organizerRepository.getById(id);
        if (organizers != null) {
            if (organizers.getIsActive() == 1) organizers.setIsActive(0);
            else organizers.setIsActive(1);
            organizerRepository.save(organizers);
        }
        return "redirect:/foradmin";
    }

    @GetMapping("/banevent/{id}")
    private String banEvents(@PathVariable("id") int id, Model model) {
        Events event = eventRepository.getById(id);
        if (event != null) {
            if (event.getIsActive() == 1) event.setIsActive(0);
            else event.setIsActive(1);
            eventRepository.save(event);
        }
        return "redirect:/foradmin";
    }

    @GetMapping("/edittype/{id}")
    private String editTypeGet(@PathVariable("id") int id, Model model) {
        if (typesRepository.getById(id) != null) {
            model.addAttribute("type", typesRepository.getById(id));
        }
        return "edittype";
    }

    @PostMapping("/edittype/{id}")
    private String editTypePost(@ModelAttribute("type") Types type) {
        typesRepository.save(type);
        return "redirect:/foradmin";
    }

    @PostMapping("/delete/{id}")
    private String deletType(@PathVariable("id") int id) {
        if (typesRepository.getById(id) != null)
            typesRepository.deleteById(id);
        return "redirect:/foradmin";
    }
}
