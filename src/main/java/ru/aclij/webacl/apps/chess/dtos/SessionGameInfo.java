package ru.aclij.webacl.apps.chess.dtos;

import aclij.pio.board.pieces.coordinates.Color;
import lombok.Getter;

public record SessionGameInfo(String sessionId, GameInfo gameInfo, Color color, long time) {

}
