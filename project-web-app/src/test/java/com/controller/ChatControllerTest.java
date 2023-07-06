package com.controller;

import com.project.controller.ChatController;
import com.project.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatControllerTest {

    @Mock
    private Model model;

    @InjectMocks
    private ChatController chatController;

    @Test
    public void testChat_ShouldReturnChatView() {
        Integer projectId = 1;

        String result = chatController.chat(model, projectId);

        verify(model, times(1)).addAttribute("projektId", projectId);
        assertEquals("chat", result);
    }
}

