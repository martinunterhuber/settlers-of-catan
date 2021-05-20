package com.example.settlersofcatan.server_client.networking.dto;

import java.io.Serializable;

public class ClientJoinedMessage extends BaseMessage implements Serializable {
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
