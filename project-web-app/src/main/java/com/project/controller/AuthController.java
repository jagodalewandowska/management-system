package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/signup")
    public String register() {
        return "register_account";
    }

    @GetMapping("/forgot-password")
    public String login() {
        return "forgotpassword";
    }
}
