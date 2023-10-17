package ru.aclij.webacl.apps.chat.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Entity
@Table(name = "chat_dialogs")
public class ChatDialog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String sessionId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chatDialog")
    private List<ChatMessage> messages;
    public ChatDialog() {
    }

    public ChatDialog(String sessionId) {
        this.sessionId = sessionId;
    }
}
