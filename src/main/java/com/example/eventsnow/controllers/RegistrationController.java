package com.example.eventsnow.controllers;


import com.example.eventsnow.model.Users;
import com.example.eventsnow.repositories.UserRepository;
import com.example.eventsnow.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final UserService userService;

    public RegistrationController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/hello")
    private String helloView(Model model) {
        return "hello";
    }

    @GetMapping("/registration")
    private String creationForm(Model model) {
        model.addAttribute("user", new Users());
        return "registration";
    }

    @PostMapping("/registration")
    private String addEvent(@ModelAttribute("user") Users user) {
        user.setIsActive(1);
        thisUser.setUserRole(roleRepository.getById(1));
        userService.saveUser(user);
        return "redirect:/login";
    }
}
