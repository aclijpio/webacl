package ru.aclij.webacl.apps.chess;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aclij.webacl.apps.chess.dtos.GameInfo;
import ru.aclij.webacl.apps.chess.exceptions.ChessSessionException;
import ru.aclij.webacl.apps.chess.exceptions.ChessSessionPlayersFullException;
import ru.aclij.webacl.apps.chess.exceptions.PlayerNotFoundException;
import ru.aclij.webacl.apps.chess.session.ChessSessionRepository;
import ru.aclij.webacl.apps.chess.session.ChessSession;
import ru.aclij.webacl.apps.chess.session.Player;
import ru.aclij.webacl.apps.chess.supp.UniqueIdGame;

import java.util.Date;
import java.util.HashMap;
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
                !chessSession.getFirstPlayer().isColor(),
                System.currentTimeMillis()
        ));
        repository.save(chessSession);
    }
    @Transactional
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
    public ResponseEntity<?> getGameInfo(String key, HttpSession httpSession){
        ChessSession chessSession = this.getChessSession(key);
        String sessionId = httpSession.getId();
        if (chessSession.isSessionExists(key) && chessSession.isPlayerExists(sessionId))
            return ResponseEntity.ok(this.createGameInfo(key));
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
}
