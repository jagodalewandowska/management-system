package com.project.controller;

import com.project.model.UserRegistrationDto;
import com.project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class UserRestController {

    private UserService userService;

    public UserRestController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "signup";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user")
                                      UserRegistrationDto registrationDto) {

        //userService.save(registrationDto);
        return "redirect:/signup?success";
    }
}