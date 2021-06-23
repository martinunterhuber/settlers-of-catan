package com.example.settlersofcatan.server_client.networking.dto;

public class ClientLeftMessage implements BaseMessage {
    public String username;

    public ClientLeftMessage() {

    }

    public ClientLeftMessage(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return username;
    }
}
