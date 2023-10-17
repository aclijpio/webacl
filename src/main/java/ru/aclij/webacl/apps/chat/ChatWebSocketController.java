package ru.aclij.webacl.apps.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.aclij.webacl.apps.chat.entities.ChatMessage;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatService service;
    @MessageMapping("/chat/{key}")
    @SendTo("/topic/response/chat/{key}")
    public Message send(@DestinationVariable String key, Message message){
        service.addChatMessage(key, ChatMessage.toChatMessage(message));
        return message;
    }
    @PostMapping("/chat/{key}/create")
    public void create(@PathVariable("key") String key){
        service.createDialog(key);
    }
    @GetMapping("/chat{key}")
    public String show(@PathVariable("key") String key){
        return "/fragments/additional/chat";
    }


}

