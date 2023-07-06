package com.project.service;

import com.project.model.Chat;
import com.project.model.FileInfo;
import com.project.repository.ChatRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {

    private ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }


    @Override
    public Page<Chat> getChat(Pageable pageable) {
        return chatRepository.findAll(pageable);
    }

    @Override
    public Optional<Chat> getChatById(Integer chatId) {
        return chatRepository.findById(chatId);
    }

    @Override
    public Chat setChat(@NotNull Chat chat) {
        Chat chatToSave = new Chat(chat.getUsername(), chat.getMessage(), chat.getProjekt());
        return chatRepository.save(chatToSave);
    }

    @Override
    @Transactional
    public void deleteChat(Integer chatId) {
        chatRepository.deleteById(chatId);
    }
}
