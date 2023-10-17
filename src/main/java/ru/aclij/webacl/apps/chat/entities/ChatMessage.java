package ru.aclij.webacl.apps.chat.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.aclij.webacl.apps.chat.Message;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "text")
    @Getter private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @ManyToOne
    @JoinColumn(name = "dialog_id")
    @Setter private ChatDialog chatDialog;
    public ChatMessage() {
    }

    public ChatMessage(String text, Date timestamp) {
        this.text = text;
        this.timestamp = timestamp;
    }
    public static ChatMessage toChatMessage(Message message){
       return new ChatMessage(message.getText(),new Timestamp(new Date().getTime()));
    }
    public static ChatMessage toChatMessage(String message){
        return new ChatMessage(message, new Timestamp(new Date().getTime()));
    }
}
