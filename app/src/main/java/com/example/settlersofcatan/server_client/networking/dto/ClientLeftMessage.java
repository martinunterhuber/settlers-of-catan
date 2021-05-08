package com.example.settlersofcatan.server_client.networking.dto;

import java.io.Serializable;

public class ClientLeftMessage extends BaseMessage implements Serializable {
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
