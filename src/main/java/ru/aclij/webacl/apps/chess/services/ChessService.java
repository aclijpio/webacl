package ru.aclij.webacl.apps.chess.services;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.aclij.webacl.apps.chat.ChatService;
import ru.aclij.webacl.apps.chat.entities.ChatMessage;
import ru.aclij.webacl.apps.chess.dtos.GameInfo;
import ru.aclij.webacl.apps.chess.dtos.SessionGameInfo;
import ru.aclij.webacl.apps.chess.exceptions.ChessSessionException;
import ru.aclij.webacl.apps.chess.exceptions.ChessSessionPlayersFullException;
import ru.aclij.webacl.apps.chess.exceptions.PlayerNotFoundException;
import ru.aclij.webacl.apps.chess.session.ChessSessionRepository;
import ru.aclij.webacl.apps.chess.session.ChessSession;
import ru.aclij.webacl.apps.chess.session.Player;
import ru.aclij.webacl.apps.chess.supp.UniqueIdGame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ChessService {
    private final UniqueIdGame uniqueIdGame;
    private final ChessSessionRepository repository;
    public String generateGame(){
        return uniqueIdGame.generateUniqueId();
    }
    public void joinToSessionById(String key, String playerId) throws ChessSessionException {
        ChessSession chessSession = repository.findByKey(key);
        if (chessSession == null)
            throw new ChessSessionException("Session not found with key " + key);
        if (chessSession.isSessionFull())
            throw new ChessSessionPlayersFullException(String.format("Chess session with key {%s} is full", key));
        chessSession.setSecondPlayer(new Player(
                playerId,
                !chessSession.getFirstPlayer().getBooleanColor(),
                System.currentTimeMillis()
        ));
        repository.save(chessSession);
    }
    public void createSession(String key, String playerId, String fenCode){
        ChessSession chessSessions = new ChessSession();
        Player player = new Player(
                playerId,
                new Random().nextBoolean(),
                System.currentTimeMillis()
        );
        chessSessions.setFirstPlayer(player);
        chessSessions.setKey(key);
        chessSessions.setFenCode(fenCode);
        repository.save(chessSessions);
    }
    public String getBoard(String key){
        return getSession(key).getFenCode();
    }
    public ChessSession getChessSession(String key){
        return repository.findByKey(key);
    }
    public Map<String, Player> getPlayers(String key) throws ChessSessionException {
        ChessSession chessSession = repository.findByKey(key);
        if (chessSession == null)
            throw new ChessSessionException("Chess session not found");
        HashMap<String, Player> players = new HashMap<>();
        players.put("firstPlayer", chessSession.getFirstPlayer());
        Player secondPlayer =  chessSession.getSecondPlayer();
        if (secondPlayer != null){
            players.put("secondPlayer", secondPlayer);
        }
        return players;
    }
    public GameInfo createGameInfo(String key){
        ChessSession chessSession = repository.findByKey(key);
        return chessSession.createGameInfo();
    }
    public ResponseEntity<?> getSessionGameInfo(String key, HttpSession httpSession){
        ChessSession chessSession = this.getChessSession(key);
        String sessionId = httpSession.getId();
        if (chessSession.isSessionExists(key) && chessSession.isPlayerExists(sessionId))
            return ResponseEntity.ok(chessSession.createSessionGameInfo(httpSession.getId()));
        return new ResponseEntity<>(new PlayerNotFoundException("Player not found with session id " + sessionId), HttpStatus.BAD_REQUEST);
    }

    public boolean isPlayerSession(String key, String sessionId) {
        return getPlayers(key).values().stream()
                .anyMatch(s -> s != null && s.getSessionId().equals(sessionId));
    }

    public void updateBoard(String key, String fenCode){
        ChessSession session = getSession(key);
        session.setFenCode(fenCode);
        repository.save(session);
    }
    private ChessSession getSession(String key){
        return repository.findByKey(key);
    }



// Main chess gaming controller functions

    public String startGame(String sessionId, Model model, ChatService chatService){
        String gameId = this.generateGame();
        model.addAttribute("gameId", gameId);
        this.createSession(gameId, sessionId, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        chatService.createDialog(gameId);
        return "redirect:/chess/" + gameId;
    }
    public String chessGame(String key, String sessionId, Model model, ChatService chatService){
        try {
            ChessSession chessSession = this.getChessSession(key);
            boolean chessSessionIsFull = chessSession.isSessionFull();
            boolean isChessSessionPlayer = chessSession.isPlayerExists(sessionId);
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
    public String joinToGame(String key, String sessionId){
        try {
            this.joinToSessionById(key, sessionId);
        } catch (ChessSessionPlayersFullException e){
            System.out.println(e.getMessage());
            return "redirect:/chess";
        }
        return "redirect:/chess/{key}";

    }
}
