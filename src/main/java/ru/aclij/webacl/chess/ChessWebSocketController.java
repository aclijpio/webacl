package ru.aclij.webacl.chess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.aclij.webacl.chess.dtos.ChessMove;

@Controller
public class ChessWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    public ChessWebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    @MessageMapping("/move")
    @SendTo("/topic/response")
    public ChessMove move(ChessMove move){
        System.out.println(move.target());
        System.out.println(move.source());  
        //messagingTemplate.convertAndSendToUser(principal.getName(), "/topic/send", "1");
        return move;
    }
    @MessageMapping("/request/start")
    @SendTo("/topic/send")
    public boolean start(){

        return true;
    }
    @MessageMapping("/request/surrender")
    @SendTo("/topic/send")
    public boolean surrender(){

        return true;
    }
}
