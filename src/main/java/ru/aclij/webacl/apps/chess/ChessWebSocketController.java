package ru.aclij.webacl.apps.chess;

import aclij.pio.board.Board;
import aclij.pio.board.BoardFactory;
import aclij.pio.game.ChessGame;
import aclij.pio.game.dto.ChessMove;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.aclij.webacl.apps.chess.dtos.ChessMoveDto;
import ru.aclij.webacl.apps.chess.dtos.GameInfo;
import ru.aclij.webacl.apps.chess.exceptions.PlayerNotFoundException;
import ru.aclij.webacl.apps.chess.services.ChessService;
import ru.aclij.webacl.apps.chess.services.ChessWebSocketService;
import ru.aclij.webacl.apps.chess.session.ChessSession;
import ru.aclij.webacl.apps.chess.session.Player;

@Controller
@RequiredArgsConstructor
public class ChessWebSocketController {
    private final ChessService chessService;
    private final ChessWebSocketService webSocketService;

    @MessageMapping("/chess/{key}")
    public void move(@DestinationVariable String key, ChessMoveDto move, @Header("sessionId") String sessionId){
        webSocketService.sendValidMoveOverWebSocket(key, move, sessionId);
    }
    @MessageMapping("/chess/surrender/{key}")
    @SendTo("/topic/send")
    public boolean surrender(){
        return true;
    }
}
