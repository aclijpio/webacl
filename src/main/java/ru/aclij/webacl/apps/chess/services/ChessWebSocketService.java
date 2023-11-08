package ru.aclij.webacl.apps.chess.services;

import aclij.pio.board.Board;
import aclij.pio.board.BoardFactory;
import aclij.pio.game.ChessGame;
import aclij.pio.game.dto.ChessMove;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aclij.webacl.apps.chess.dtos.ChessMoveDto;
import ru.aclij.webacl.apps.chess.exceptions.PlayerNotFoundException;
import ru.aclij.webacl.apps.chess.session.ChessSession;
import ru.aclij.webacl.apps.chess.session.Player;

@Service
@RequiredArgsConstructor
public class ChessWebSocketService {

    private final ChessService chessService;
    private final WebSocketSenderService sender;

    public void sendValidMoveOverWebSocket(String key, ChessMoveDto move, String sessionId){
        ChessSession chessSession = chessService.getChessSession(key);
        Board board = BoardFactory.fromFen(chessSession.getFenCode());
        ChessGame chessGame = new ChessGame(board);
        ChessMove chessMove = move.toChessMove(board);
        Player player = chessSession.getPlayerExists(sessionId).orElseThrow(() -> new PlayerNotFoundException("Player not found with session id : " + sessionId));
        if (player.isPieceOwnedBy(chessMove.getSource(board)) && chessGame.move(chessMove)) {
            String fenCode =  chessGame.getBoard().toFen();
            chessService.updateBoard(key, fenCode);
            sender.send(key, chessService.createGameInfo(key));
        }
    }
}
