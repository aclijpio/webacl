package ru.aclij.webacl.apps.chess;

import aclij.pio.board.Board;
import aclij.pio.board.BoardFactory;
import aclij.pio.board.pieces.Piece;
import aclij.pio.game.CheckMate;
import aclij.pio.game.ChessGame;
import aclij.pio.game.MoveValidator;
import aclij.pio.game.dto.ChessMove;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.aclij.webacl.apps.chess.dtos.ChessMoveDto;
import ru.aclij.webacl.apps.chess.dtos.GameInfo;
import ru.aclij.webacl.apps.chess.exceptions.PlayerNotFoundException;
import ru.aclij.webacl.apps.chess.session.ChessSession;
import ru.aclij.webacl.apps.chess.session.Player;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ChessWebSocketController {
    private final ChessService service;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chess/{key}")
    public void move(@DestinationVariable String key, ChessMoveDto move, @Header("sessionId") String sessionId){
        ChessSession chessSession = service.getChessSession(key);
        Board board = BoardFactory.fromFen(chessSession.getFenCode());
        ChessGame chessGame = new ChessGame(board);
        ChessMove chessMove = move.toChessMove(board);
        Player player = chessSession.getPlayerExists(sessionId).orElseThrow(() -> new PlayerNotFoundException("Player not found with session id : " + sessionId));
        if (player.isPieceOwnedBy(chessMove.getSource(board)) && chessGame.move(chessMove)) {
            String fenCode =  chessGame.getBoard().toFen();
            service.updateBoard(key, fenCode);
            saveAndSend(key, service.createGameInfo(key));
        }
    }
    @MessageMapping("/request/chess/{key}/surrender")
    @SendTo("/topic/send")
    public boolean surrender(){
        return true;
    }

    private void saveAndSend(String key, GameInfo gameInfo){
        service.updateBoard(key, gameInfo.fenCode());
        messagingTemplate.convertAndSend("/topic/response/chess/" + key, gameInfo);

    }
}
