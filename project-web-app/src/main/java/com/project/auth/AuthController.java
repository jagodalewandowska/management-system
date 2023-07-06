package com.project.auth;

import com.project.model.Student;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

@Controller
@RequiredArgsConstructor
@Slf4j
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
        try {
            tokenHolder.setToken(authService.login(credentials).get());
        } catch (HttpStatusCodeException e) {
            bindingResult.rejectValue(Strings.EMPTY, String.valueOf(e.getStatusCode().value()), e.getStatusCode() + " - Nieprawidłowy e-mail lub hasło!");
            return "login";
        }
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
        try {
            tokenHolder.clear();
            authService.register(student);
        } catch (HttpStatusCodeException e) {
            bindingResult.rejectValue(Strings.EMPTY, String.valueOf(e.getStatusCode().value()), e.getStatusCode().toString());
            if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
                bindingResult.rejectValue(Strings.EMPTY, "403", "Użytkownik istnieje już w bazie danych.");
            }
            return "register";
        }
        return "redirect:/app/login";
    }

    @PostMapping(params = "cancel", path = "register")
    public String register() {
        return "redirect:/app/login";
    }
}
