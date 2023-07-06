package com.project.controller;

import com.project.model.Chat;
import com.project.model.FileInfo;
import com.project.repository.ChatRepository;
import com.project.service.ChatService;
import com.project.service.ProjektService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class ChatController {

  private ChatService chatService;
  private ChatRepository chatRepository;

  public ChatController(ChatService chatService, ChatRepository chatRepository) {
    this.chatService = chatService;
    this.chatRepository = chatRepository;
  }

  @GetMapping("/chats")
  Page<Chat> getFiles(Pageable pageable) {
      return chatService.getChat(pageable);
  }

  @GetMapping("/chats/{chatId}")
  ResponseEntity<Chat> getChatById(@PathVariable Integer chatId) {
    return ResponseEntity.of(chatService.getChatById(chatId));
  }

  @GetMapping("/chats/projektId={projektId}")
  public Page<Chat> getFilesByProjektId(@PathVariable Integer projektId, Pageable pageable) {
    return chatRepository.findByProjektProjektId(projektId, pageable);
  }

  @PostMapping("/chats")
  public ResponseEntity<Void> createFile(@Valid @RequestBody Chat chat) {
    Chat addedChat = chatService.setChat(chat);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{chatId}")
            .buildAndExpand(addedChat.getChatId()).toUri();
    return ResponseEntity.created(location).build();
  }

  @DeleteMapping(path = "/chats/{chatId}")
  public ResponseEntity<Void> deleteFile(@PathVariable Integer chatId) {
    return chatService.getChatById(chatId).map(p -> {
      chatService.deleteChat(chatId);
      return new ResponseEntity<Void> (HttpStatus.OK);
    }).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
