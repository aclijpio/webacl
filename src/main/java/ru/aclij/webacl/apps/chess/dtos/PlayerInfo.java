package ru.aclij.webacl.apps.chess.dtos;

import lombok.Getter;
import ru.aclij.webacl.apps.chess.session.Player;
@Getter
public class PlayerInfo {
    private final long currentTime;
    private final boolean color;

    public PlayerInfo(long currentTime, boolean color) {
        this.currentTime = currentTime;
        this.color = color;
    }

    public PlayerInfo(Player player) {
        this.currentTime = player.getCurrentTime();
        this.color = player.isColor();
    }
}
