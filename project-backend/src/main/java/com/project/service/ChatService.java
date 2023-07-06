package com.project.service;

import com.project.model.Chat;
import com.project.model.FileInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ChatService {

    Page<Chat> getChat(Pageable pageable);

    Optional<Chat> getChatById(Integer chatId);

    Chat setChat(Chat chat);

    @Transactional
    void deleteChat(Integer fileId);
}
