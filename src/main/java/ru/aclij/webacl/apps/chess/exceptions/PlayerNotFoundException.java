package ru.aclij.webacl.apps.chess.exceptions;

public class PlayerNotFoundException extends ChessSessionException{
    public PlayerNotFoundException(String message) {
        super(message);
    }

    public PlayerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlayerNotFoundException(Throwable cause) {
        super(cause);
    }
}
