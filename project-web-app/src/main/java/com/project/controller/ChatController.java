package com.project.controller;

import com.project.service.StudentService;
import com.project.service.ZadanieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/app")
@Slf4j
public class ChatController {

    private StudentService studentService;

    @GetMapping("/chat")
    public String chat(Model model, @RequestParam("projektId") Integer projektId) {
        model.addAttribute("projektId", projektId);
//        model.addAttribute("username", studentService.getStudent() + " " + nazwisko);
        return "chat";
    }
}
