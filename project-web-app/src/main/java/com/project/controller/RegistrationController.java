package com.project.controller;

import com.project.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import com.project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
@EnableAutoConfiguration
public class RegistrationController {

    @Autowired
    private UserService userSerice;

    @GetMapping({"/register_account"})
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());
        System.out.println("TUTAJ");
        return "register_account";
    }

    @PostMapping("/process_register")
    public String processRegister(final @Valid User user, final BindingResult bindingResult, final Model model){
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "register_account";
        }
        userSerice.register(user);
        System.out.println("REDIRECT");
        return "/";
    }
//    private UserService userService;
//
//    public RegistrationController(UserService userService) {
//        super();
//        this.userService = userService;
//    }
//
//    @ModelAttribute("user")
//    public UserRegistrationDto userRegistrationDto() {
//        return new UserRegistrationDto();
//    }
//
//    @GetMapping
//    public String showRegistrationForm() {
//        return "registration";
//    }
//
//    @PostMapping
//    public String registerUserAccount(@ModelAttribute("user")
//                                      UserRegistrationDto registrationDto) {
//
//        //userService.save(registrationDto);
//        return "redirect:/registration?success";
//    }
}