package ru.aclij.webacl.apps.chess.exceptions;

public class ChessSessionException extends RuntimeException{
    String message;
    public ChessSessionException(String message) {
        super(message);
        this.message = message;
    }

    public ChessSessionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChessSessionException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
