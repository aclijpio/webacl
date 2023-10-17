package ru.aclij.webacl.apps.chat.exceptions;
public class DialogNotFoundException extends RuntimeException {
    public DialogNotFoundException(String message) {
        super(message);
    }

    public DialogNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
