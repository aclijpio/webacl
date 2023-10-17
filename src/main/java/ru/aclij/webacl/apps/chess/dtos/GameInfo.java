package ru.aclij.webacl.apps.chess.dtos;

import lombok.Getter;
import ru.aclij.webacl.apps.chess.session.Player;

import java.text.SimpleDateFormat;


public record GameInfo(String fenCode, PlayerInfo firstPlayer, PlayerInfo secondPlayer) {
    private final static SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");

}
