package com.project.controller;

import com.project.auth.TokenHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Base64;

@Controller
@RequestMapping("/app")
@Slf4j
public class ChatController {

    Base64.Decoder decoder = Base64.getUrlDecoder();
    private final TokenHolder tokenHolder;

    public ChatController(TokenHolder tokenHolder) {
        this.tokenHolder = tokenHolder;
    }

    @GetMapping("/chat")
    public String chat(Model model, @RequestParam("projektId") Integer projektId) {

        final String token = tokenHolder.getToken();
        String[] chunks = token.split("\\.");
        final String payload = new String(decoder.decode(chunks[1]));
        String username = "";

        log.info("Interceptor's token ({})", token);
        log.info("before substring ({})", payload);

        String temp_var = payload.substring(8);

        for (int i = 0; i < temp_var.length(); i++) {
            if(temp_var.charAt(i) == '\"') {break;}
            username += temp_var.charAt(i);
        }

        log.info("username ({})", username);

        model.addAttribute("projektId", projektId);
        model.addAttribute("username", username);
        return "chat";
    }
}
