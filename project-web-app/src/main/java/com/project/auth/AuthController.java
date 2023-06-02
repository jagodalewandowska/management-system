package com.project.auth;

import com.project.model.Student;
import com.project.model.Tutor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app")
public class AuthController {
    private final AuthService authService;
    private final TokenHolder tokenHolder;

    @GetMapping("/login")
    public String login (Model model) {
        model.addAttribute("credentials", Credentials.builder().build());
        return "login";
    }

    @PostMapping(path = "/login")
    public String login (@ModelAttribute @Valid Credentials credentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "login";
        tokenHolder.setToken(authService.login(credentials).get());
        return "redirect:/app/projektList";
    }

    @GetMapping("/logout")
    public String logout() {
        tokenHolder.clear();
        return "redirect:/app/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("student", new Student());
        return "register";
    }

    @PostMapping(path = "/register")
    public String register (@ModelAttribute @Valid Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "register";
        tokenHolder.clear();
        authService.register(student);
        return "redirect:/app/login";
    }

    @GetMapping("/registerTutor")
    public String registerTutor(Model model) {
        model.addAttribute("tutor", new Tutor());
        return "signup";
    }

    @PostMapping(path = "/registerTutor")
    public String registerTutor(@ModelAttribute @Valid Tutor tutor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "signup";
        tokenHolder.clear();
        authService.registerTutor(tutor);
        return "redirect:/app/login";
    }

    @PostMapping(params = "cancel", path = "register")
    public String register() {
        return "redirect:/app/login";
    }
}
