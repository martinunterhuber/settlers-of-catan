package com.example.settlersofcatan.server_client.networking.dto;

public class TextMessage implements BaseMessage {

    public TextMessage() {
    }

    public TextMessage(String text) {
        this.text = text;
    }

    public String text;

    @Override
    public String toString() {
        return String.format("TextMessage: %s", text);
    }
}
