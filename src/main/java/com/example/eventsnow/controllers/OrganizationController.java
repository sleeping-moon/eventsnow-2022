package com.example.eventsnow.controllers;

import com.example.eventsnow.model.Events;
import com.example.eventsnow.model.Organizers;
import com.example.eventsnow.model.Types;
import com.example.eventsnow.model.Users;
import com.example.eventsnow.repositories.*;
import com.example.eventsnow.services.CloudinaryService;
import com.example.eventsnow.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/organization")
public class OrganizationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class);
    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;
    private final UserRepository userRepository;
    private final TypeRepository typesRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;


    public OrganizationController(EventRepository eventRepository, OrganizerRepository organizerRepository,
                                  UserRepository userRepository, UserService userService, TypeRepository typesRepository,
                                  CloudinaryService cloudinaryService, RoleRepository roleRepository) {
        this.eventRepository = eventRepository;
        this.organizerRepository = organizerRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.typesRepository = typesRepository;
        this.cloudinaryService = cloudinaryService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    private String organization(Model model, Principal principal) {
        Users thisuser = userRepository.findByLogin(principal.getName());
        if (thisuser.getOrganizerId() != 0) {
            Organizers organization = organizerRepository.getById(thisuser.getOrganizerId());
            if (organization.getIsActive() == 1) {
                List<Users> organizationUser = userRepository.getUsersByOrganizerId(thisuser.getOrganizerId());
                model.addAttribute("organizationUser", organizationUser);
                model.addAttribute("organization", organization);
                model.addAttribute("thisuser", thisuser);
                model.addAttribute("newMemder", "");
                return "organization";
            } else {
                return "unavalibleorganization";
            }
        } else {
            return "redirect:/homepage";
        }
    }

    @GetMapping("/add")
    private String creationForm(Model model, Principal principal) {
        Users thisuser = userRepository.findByLogin(principal.getName());
        Organizers organizer = organizerRepository.getById(thisuser.getOrganizerId());
        if (organizer.getIsActive() == 1) {
            Events event = new Events();
            event.setTypes(new ArrayList<>());
            for (int i = 0; i < 5; i++) event.addTypes(new Types());
            List<Types> types = typesRepository.findAll();
            List<Organizers> allOrganizer = organizerRepository.findAll();
            model.addAttribute("event", event);
            model.addAttribute("thisorganizer", thisuser.getOrganizerId());
            model.addAttribute("type", types);
            model.addAttribute("organizer", allOrganizer);
            return "add";
        }
        return "redirect:/organization";
    }

    @PostMapping("/add")
    private String addEvent(@ModelAttribute("thisevent") Events event,
                            @RequestParam("file") MultipartFile file,
                            @RequestParam("file2") MultipartFile file2,
                            Principal principal) {
        Users thisUser = userRepository.findByLogin(principal.getName());
        Organizers organizer = organizerRepository.getById(thisUser.getOrganizerId());
        if (organizer.getIsActive() == 1) {
            event.setCreationDate(new Date());
            event.setIsActive(1);
            event.addEventOrganizers(organizerRepository.getById(thisUser.getOrganizerId()));
            event.setDateOfEvent(Events.getDateFromString(event.getDateOfEventInString()));
            String mimetype = file.getContentType();
            String type = mimetype.split("/")[0];
            String mimetype2 = file2.getContentType();
            String type2 = mimetype2.split("/")[0];
            if (!file.isEmpty() && type.equals("image")
                    && !file2.isEmpty() && type2.equals("image")
                    && event.getNumberOfPlaces()> 0 && event.getTitle().length() < 50
                    && event.getDescription().length() < 500
                    && file.getSize()<2048576) {
                event.setSecondPoster(cloudinaryService.uploadFile(file2));
                event.setPoster(cloudinaryService.uploadFile(file));
                LOGGER.info(thisUser.getLogin() + " (Id:" + thisUser.getUserId() + ") created the event "
                        + event.getTitle() + " (Id:" + event.getEventId() + ") Created by the organization:" + organizerRepository.getById(thisUser.getOrganizerId()).getTitle());
                eventRepository.save(event);
            } else {
                return "redirect:/organization/add?error";
            }
        }
        return "redirect:/organization";
    }

    @GetMapping("/event/{id}/edit")
    private String editForm(@PathVariable("id") int id,
                            Model model,
                            Principal principal) {
        Users thisUser = userRepository.findByLogin(principal.getName());
        Organizers organizer = organizerRepository.getById(thisUser.getOrganizerId());
        Events event = eventRepository.getById(id);
        if (thisUser.isUserEvent(event) && organizer.getIsActive() == 1) {
            event.setTypes(new ArrayList<>());
            for (int i = 0; i < 5; i++) event.addTypes(new Types());
            event.setDateOfEventInString(Events.getStringFromDate(event.getDateOfEvent()));
            List<Types> types = typesRepository.findAll();
            model.addAttribute("event", event);
            model.addAttribute("type", types);
            return "edit";
        }
        return "redirect:/homepage";
    }

    @PostMapping("/event/{id}/edit")
    private String editEvent(@ModelAttribute("event") Events event,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam("file2") MultipartFile file2,
                             Principal principal) {
        Users thisUser = userRepository.findByLogin(principal.getName());
        Organizers organizer = organizerRepository.getById(thisUser.getOrganizerId());
        String mimetype = file.getContentType();
        String type = mimetype.split("/")[0];
        String mimetype2 = file2.getContentType();
        String type2 = mimetype2.split("/")[0];
        if (organizer.getIsActive() == 1
                && event.getNumberOfPlaces()> 0 && event.getTitle().length() < 50
                && event.getDescription().length() < 500) {
            if(!file.isEmpty() && type.equals("image")) event.setPoster(cloudinaryService.uploadFile(file));
            if(!file2.isEmpty() && type2.equals("image")) event.setSecondPoster(cloudinaryService.uploadFile(file2));
            event.setCreationDate(new Date());
            event.setDateOfEvent(Events.getDateFromString(event.getDateOfEventInString()));
            LOGGER.info(thisUser.getLogin() + " (Id:" + thisUser.getUserId() + ") update the event "
                    + event.getTitle() + " (Id:" + event.getEventId() + ") Organizer Id:"
                    + thisUser.getOrganizerId());
            eventRepository.save(event);
            return "redirect:/organization";
        }
        return "/event/"+event.getEventId()+"/edit$error";
    }


    @PostMapping("/event/{id}/delete")
    private String deleteEvent(@PathVariable("id") int id,
                               Principal principal) {
        Users thisUser = userRepository.findByLogin(principal.getName());
        Organizers organizer = organizerRepository.getById(thisUser.getOrganizerId());
        if (thisUser.isUserEvent(eventRepository.getById(id)) && organizer.getIsActive() == 1) {
            Events event = eventRepository.getById(id);
            LOGGER.info(thisUser.getLogin() + " (Id:" + thisUser.getUserId() + ") delete the event "
                    + event.getTitle() + " (Id:" + event.getEventId() + ") Organizer Id:"
                    + thisUser.getOrganizerId());
            eventRepository.deleteById(id);
        }
        return "redirect:/organization";
    }

    @GetMapping("/removefromorganization/{id}")
    private String removeFromOrganization(@PathVariable("id") int id,
                                           Principal principal) {
        Users thisUser = userRepository.findByLogin(principal.getName());
        Organizers organizer = organizerRepository.getById(thisUser.getOrganizerId());
        Users deletedUser = userRepository.getById(id);
        if ((thisUser.getUserRole().getId() == 4 || deletedUser.getUserId() == thisUser.getUserId())
                && deletedUser != null && deletedUser.getUserRole().getId() != 4
                && organizer.getIsActive() == 1) {
            LOGGER.info(thisUser.getLogin() + " (Id:" + thisUser.getUserId() + ") removed from organization "
                    + " Organizer Id:" + deletedUser.getOrganizerId() + ")");
            deletedUser.setOrganizerId(0);
            deletedUser.setUserRole(roleRepository.getById(1));
            userService.updateUser(deletedUser);
        } else if ((thisUser.getUserRole().getId() == 4 && deletedUser.getUserId() == thisUser.getUserId())
                && deletedUser != null) {
            List<Users> organizationUsers = userRepository.getUsersByOrganizerId(thisUser.getOrganizerId());
            if (organizationUsers.size() > 1) {
                int i = 0;
                while (i < organizationUsers.size()) {
                    if (organizationUsers.get(i).getUserId() != deletedUser.getUserId()) {
                        LOGGER.info(thisUser.getLogin() + " (Id:" + thisUser.getUserId() + ") removed from organization "
                                + " Organizer Id:" + deletedUser.getOrganizerId() + ") New boss "
                                + organizationUsers.get(i).getLogin() + " (Id:" + organizationUsers.get(i).getUserId() + ")");
                        organizationUsers.get(i).setUserRole(roleRepository.getById(4));
                        deletedUser.setOrganizerId(0);
                        deletedUser.setUserRole(roleRepository.getById(1));
                        userService.updateUser(organizationUsers.get(i));
                        userService.updateUser(deletedUser);
                        break;
                    } else i++;
                }
            }
        }
        return "redirect:/organization";
    }

    @PostMapping("/addtoorganization")
    private String addNewMemder(@ModelAttribute("newMemder") String newMemder,
                                Principal principal) {
        Users newUser = userRepository.findByLogin(newMemder);
        Users thisUser = userRepository.findByLogin(principal.getName());
        Organizers organizer = organizerRepository.getById(thisUser.getOrganizerId());
        if (thisUser.getUserRole().getId() == 4 && newUser != null &&
                newUser.getUserRole().getId() == 1 && organizer.getIsActive() == 1) {
            newUser.setUserRole(roleRepository.getById(2));
            newUser.setOrganizerId(thisUser.getOrganizerId());
            userService.updateUser(newUser);
            LOGGER.info(newUser.getLogin() + " (Id:" + newUser.getUserId() + ") add to organization "
                    + " Organizer Id:" + thisUser.getOrganizerId());
        }
        return "redirect:/organization";
    }

    @GetMapping("/makeboss/{id}")
    private String makeBoss(@PathVariable("id") int id,
                            Principal principal) {
        Users thisUser = userRepository.findByLogin(principal.getName());
        Organizers organizer = organizerRepository.getById(thisUser.getOrganizerId());
        Users newBoss = userRepository.getById(id);
        if (thisUser.getUserRole().getId() == 4 &&
                newBoss.getOrganizerId() == thisUser.getOrganizerId() &&
                organizer.getIsActive() == 1) {
            newBoss.setUserRole(roleRepository.getById(4));
            thisUser.setUserRole(roleRepository.getById(2));
            userService.updateUser(newBoss);
            userService.updateUser(thisUser);
            LOGGER.info(thisUser.getLogin() + " (Id:" + thisUser.getUserId() + ") is no longer the boss of the organization"
                    + " Organizer Id:" + thisUser.getOrganizerId() + ") New boss "
                    + newBoss.getLogin() + " (Id:" + newBoss.getUserId() + ")");
        }
        return "redirect:/organization";
    }

    @PostMapping("/updategroup")
    private String editOrganization(Principal principal) {
        Users thisUser = userRepository.findByLogin(principal.getName());
        Organizers organizers = organizerRepository.getById(thisUser.getOrganizerId());
        if (thisUser.getUserRole().getId() == 4 &&
                thisUser.getOrganizerId() == organizers.getOrganizerId() &&
                organizers.getIsActive() == 1) organizerRepository.save(organizers);
        return "redirect:/organization";
    }
}
