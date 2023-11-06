package ru.aclij.webacl.apps.chess;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.aclij.webacl.apps.chat.ChatService;
import ru.aclij.webacl.apps.chat.entities.ChatMessage;
import ru.aclij.webacl.apps.chess.exceptions.ChessSessionException;
import ru.aclij.webacl.apps.chess.exceptions.ChessSessionPlayersFullException;
import ru.aclij.webacl.apps.chess.session.ChessSession;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChessController {
    private final ChessService service;
    private final ChatService chatService;
    private final static String startPosition = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    @GetMapping("/chess")
    public String startChess(){

        return "/pages/chess/start";
    }
    @GetMapping("/chess/start")
    public String start(Model model, HttpSession session){
        String gameId = service.generateGame();
        model.addAttribute("gameId", gameId);
        service.createSession(gameId, session.getId(), "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        chatService.createDialog(gameId);
        return "redirect:/chess/" + gameId;
    }
    @GetMapping("/chess/{key}")
    public String chess(@PathVariable("key") String key, HttpSession session, Model model){
        try {
            ChessSession chessSession = service.getChessSession(key);
            boolean chessSessionIsFull = chessSession.isSessionFull();
            boolean isChessSessionPlayer = chessSession.isPlayerExists(session.getId());
            if (isChessSessionPlayer){
                if (chessSessionIsFull){
                    List<ChatMessage> messages = chatService.getMessages(key);
                    model.addAttribute("messages", messages);
                    return "/pages/chess/game";
                } else {
                    return "/pages/chess/wait";
                }
            }else {
                if (chessSessionIsFull){
                    return "redirect:/chess";
                } else
                {
                    model.addAttribute("key", key);
                    return "/pages/chess/join";
                }
            }
        } catch (ChessSessionException e){
            return "redirect:/chess";
        }
    }
    @GetMapping("/chess/join/{key}")
    public String joinToGame(@PathVariable("key") String key, HttpSession httpSession){
        try {
            service.joinToSessionById(key, httpSession.getId());
        } catch (ChessSessionPlayersFullException e){
            System.out.println(e.getMessage());
            return "redirect:/chess";
        }
        return "redirect:/chess/{key}";
    }
    @PostMapping("/chess/playerInfo/{key}")
    public ResponseEntity<?> getGameInfo(@PathVariable("key") String key, HttpSession httpSession){
        return service.getSessionGameInfo(key, httpSession);
    }
}
