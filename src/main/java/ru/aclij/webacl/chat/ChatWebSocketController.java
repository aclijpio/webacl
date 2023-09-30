package ru.aclij.webacl.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    public ChatWebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    @MessageMapping("/chat")
    @SendTo("topic/request")
    public String move(String message){
        System.out.println(message);
        return message;
    }
}

