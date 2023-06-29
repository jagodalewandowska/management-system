package com.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
@Slf4j
public class ChatController {
    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }
}
