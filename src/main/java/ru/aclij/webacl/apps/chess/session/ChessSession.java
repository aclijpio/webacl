package ru.aclij.webacl.apps.chess.session;

import jakarta.persistence.*;
import lombok.Data;
import ru.aclij.webacl.apps.chess.dtos.GameInfo;
import ru.aclij.webacl.apps.chess.dtos.PlayerInfo;

import java.util.Optional;

@Entity
@Table(name = "chess_sessions")
@Data
public class ChessSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "key")
    private String key;

    @Column(name = "fen_code")
    private String fenCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "first_user_id")
    private Player firstPlayer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "second_user_id")
    private Player secondPlayer;
    public boolean isSessionFull(){
        return firstPlayer != null && secondPlayer != null;
    }
    public boolean isSessionExists(String key) {
        return this.key.equals(key);
    }
    public boolean isPlayerExists(String sessionId){
        return     firstPlayer != null && this.firstPlayer.getSessionId().equals(sessionId)
                || secondPlayer != null && this.secondPlayer.getSessionId().equals(sessionId);
    }
    public Optional<Player> getPlayerExists(String sessionId){
        if (firstPlayer != null && this.firstPlayer.getSessionId().equals(sessionId))
            return Optional.of(firstPlayer);
        if (secondPlayer != null && this.secondPlayer.getSessionId().equals(sessionId))
            return Optional.of(secondPlayer);
        return Optional.empty();
    }
    public GameInfo createGameInfo(){
        return new GameInfo(
                this.fenCode,
                new PlayerInfo(this.firstPlayer),
                new PlayerInfo(this.secondPlayer)
        );
    }
}
