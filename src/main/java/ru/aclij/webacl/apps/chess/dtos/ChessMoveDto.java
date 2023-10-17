package ru.aclij.webacl.apps.chess.dtos;

import aclij.pio.board.Board;
import aclij.pio.game.dto.ChessMove;

public record ChessMoveDto(String source, String target) {

    public ChessMove toChessMove(Board board){
        return new ChessMove(source, target, board);
    }

    @Override
    public String toString() {
        return "ChessMoveDto{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}
