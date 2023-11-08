package ru.aclij.webacl.apps.chess;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.aclij.webacl.apps.chat.ChatService;
import ru.aclij.webacl.apps.chat.entities.ChatMessage;
import ru.aclij.webacl.apps.chess.exceptions.ChessSessionException;
import ru.aclij.webacl.apps.chess.exceptions.ChessSessionPlayersFullException;
import ru.aclij.webacl.apps.chess.properties.ChessProperties;
import ru.aclij.webacl.apps.chess.services.ChessService;
import ru.aclij.webacl.apps.chess.session.ChessSession;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChessController {
    private final ChessService service;
    private final ChatService chatService;
    private final ChessProperties chessProperties;
    private final static String startPosition = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    @GetMapping("/chess")
    public String startChess(){

        return "/pages/chess/start";
    }
    @GetMapping("/chess/start")
    public String start(HttpSession session, Model model){
        return service.startGame(session.getId(), model, chatService);
    }
    @GetMapping("/chess/{key}")
    public String chess(@PathVariable("key") String key, HttpSession session, Model model){
        return service.chessGame(key, session.getId(), model, chatService);
    }
    @GetMapping("/chess/join/{key}")
    public String joinToGame(@PathVariable("key") String key, HttpSession httpSession){
        return service.joinToGame(key, httpSession.getId());
    }
    @PostMapping("/chess/playerInfo/{key}")
    public ResponseEntity<?> getGameInfo(@PathVariable("key") String key, HttpSession httpSession){
        return service.getSessionGameInfo(key, httpSession);
    }
}
