package ru.aclij.webacl.apps.chat;

import lombok.Data;
@Data
public class Message {
    private String text;

    public Message() {
    }

    public Message(String text) {
        this.text = text;
    }
}
