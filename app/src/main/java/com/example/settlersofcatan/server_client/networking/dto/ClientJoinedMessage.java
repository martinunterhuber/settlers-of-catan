package com.example.settlersofcatan.server_client.networking.dto;

public class ClientJoinedMessage implements BaseMessage {
    public String username;

    public ClientJoinedMessage() {

    }

    public ClientJoinedMessage(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return username;
    }
}
