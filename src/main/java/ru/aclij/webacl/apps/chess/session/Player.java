package ru.aclij.webacl.apps.chess.session;

import aclij.pio.board.pieces.Piece;
import aclij.pio.board.pieces.coordinates.Color;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Entity
@Table(name = "players")
@Data
@NoArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long id;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "color")
    private boolean color;

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
        return color && piece.color == Color.WHITE;
    }
}






