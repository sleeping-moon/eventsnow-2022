package com.example.eventsnow.controllers;

import com.example.eventsnow.model.Organizers;
import com.example.eventsnow.model.Roles;
import com.example.eventsnow.model.Types;
import com.example.eventsnow.model.Users;
import com.example.eventsnow.repositories.OrganizerRepository;
import com.example.eventsnow.repositories.RoleRepository;
import com.example.eventsnow.repositories.TypeRepository;
import com.example.eventsnow.repositories.UserRepository;
import com.example.eventsnow.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/profilesettings")
public class ProfileController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);
    private final UserRepository userRepository;
    private final TypeRepository typesRepository;
    private final UserService userService;
    private final OrganizerRepository organizerRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ProfileController(UserRepository userRepository, UserService userService,
                             TypeRepository typesRepository,OrganizerRepository organizerRepository,
                             RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.typesRepository = typesRepository;
        this.organizerRepository = organizerRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    private String printAllEvents(Model model, Principal principal) {
        List<Types> types = typesRepository.findAll();
        Users user = userRepository.findByLogin(principal.getName());
        String oldpassword = "";
        String newpassword = "";
        String repeatpassword = "";
        Organizers organization = new Organizers();
        model.addAttribute("oldpassword", oldpassword);
        model.addAttribute("newpassword", newpassword);
        model.addAttribute("organization", organization);
        model.addAttribute("repeatpassword", repeatpassword);
        model.addAttribute("type", types);
        model.addAttribute("user", user);
        return "profilesettings";
    }

    @PostMapping
    private String updateUser(@ModelAttribute("user") Users user) {
        if(userRepository.findByLogin(user.getLogin()) == null
                && user.getLogin().length() < 40
                && user.getLastName().length() < 50
                && user.getFirstName().length() < 50){
           userService.updateUser(user);
           LOGGER.info(user.getLogin() + " (Id:" + user.getUserId() + ") edited the profile");
        }
        return "redirect:/profilesettings";
    }

    @PostMapping("/changepassword")
    private String changePassword(@ModelAttribute("oldpassword") String oldpassword, @ModelAttribute("newpassword") String newpassword,
                                  @ModelAttribute("repeatpassword") String repeatpassword, Principal principal) {
        Users thisUser = userRepository.findByLogin(principal.getName());
        if (bCryptPasswordEncoder.matches(oldpassword, thisUser.getPassword()) && newpassword.length()< 40)
            if (newpassword.equals(repeatpassword)) {
                thisUser.setPassword(bCryptPasswordEncoder.encode(newpassword));
                userRepository.save(thisUser);
                LOGGER.info(thisUser.getLogin() + " (Id:" + thisUser.getUserId() + ") updated the password");
            }
        return "redirect:/profilesettings";
    }

    @PostMapping("/creategroup")
    private String createGroup(@ModelAttribute("organization") Organizers organization, Principal principal) {
        Users thisUser = userRepository.findByLogin(principal.getName());
        if(thisUser.getUserRole().getId() == 1
                && organization.getTitle().length() < 50
                && organization.getEmail().length() < 500
                && organization.getDescription().length() < 500){
            thisUser.setUserRole(roleRepository.getById(4));
            organization.setIsActive(0);
            organizerRepository.save(organization);
            thisUser.setOrganizerId(organization.getOrganizerId());
            userRepository.save(thisUser);
            LOGGER.info(thisUser.getLogin() + " (Id:" + thisUser.getUserId() + ") create group (Id:" + organization.getOrganizerId() + ")"
                    + organization.getTitle());
        }
        return "redirect:/profilesettings";
    }
}
