package ru.aclij.webacl.apps.chess.exceptions;


public class ChessSessionPlayersFullException extends ChessSessionException {

    public ChessSessionPlayersFullException(String message) {
        super(message);
    }

    public ChessSessionPlayersFullException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChessSessionPlayersFullException(Throwable cause) {
        super(cause);
    }
}
