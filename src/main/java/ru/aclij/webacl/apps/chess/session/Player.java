package ru.aclij.webacl.apps.chess.session;

import aclij.pio.board.pieces.Piece;
import aclij.pio.board.pieces.coordinates.Color;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "players")
@NoArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long id;

    @Getter
    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "color")
    private boolean color;

    @Getter
    @Column(name = "time")
    private long time;

    public Player(String sessionId, boolean color, long time) {
        this.sessionId = sessionId;
        this.color = color;
        this.time = time;
    }
    public long getCurrentTime() {
        return System.currentTimeMillis() - this.time;
    }
    public boolean isPieceOwnedBy(Piece piece){

        return getColor() == piece.color;
    }
    public Color getColor() {
        return color ? Color.WHITE : Color.BLACK;
    }
    public boolean getBooleanColor(){
        return color;
    }

}






