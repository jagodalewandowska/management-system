package com.project.controller;
import com.project.model.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.project.service.UserService;
import com.project.model.UserRegistrationDto;
@Controller
public class RegistrationController {

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
//        userService.save(registrationDto);
//        return "redirect:/registration?success";
//    }
    @GetMapping("/register")
    public String showRegistrationForm(final Model model){
        model.addAttribute("user", new User());
        return "account/register";
    }

    @PostMapping("/register")
    public String userRegistration(final @Valid User user, final BindingResult bindingResult, final Model model){
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "account/register";
        }

        return "redirect:account/starter";
    }
}