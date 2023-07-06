package com.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chat_id")
    private Integer chatId;

    @Column(name = "username")
    private String username;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JsonIgnoreProperties({"projekt"})
    @JoinColumn(name = "projekt_id")
    private Projekt projekt;

    public Chat() {}

    public Chat(Integer chatId, String username, String message, Projekt projekt) {
        this.chatId = chatId;
        this.username = username;
        this.message = message;
        this.projekt = projekt;
    }

    public Chat(String username, String message, Projekt projekt) {
        this.username = username;
        this.message = message;
        this.projekt = projekt;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        this.projekt = projekt;
    }
}
