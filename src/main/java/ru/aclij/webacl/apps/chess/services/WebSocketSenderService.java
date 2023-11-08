package ru.aclij.webacl.apps.chess.services;


import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.aclij.webacl.apps.chess.properties.ChessWebSocketProperties;

@Service
@RequiredArgsConstructor
public class WebSocketSenderService {
    private final ChessWebSocketProperties properties;
    private final SimpMessagingTemplate messagingTemplate;


    public <T> void send(String key, T info){
        messagingTemplate.convertAndSend(properties.getResponseUrl() + key, info);
    }
    public <T> void send(T info){
        messagingTemplate.convertAndSend(properties.getResponseUrl(), info);
    }

}
