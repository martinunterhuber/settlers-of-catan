package com.example.settlersofcatan.server_client.networking.dto;

public class ClientDiceMessage extends BaseMessage {
    private String username;

    private int rolled;

    public ClientDiceMessage(){}

    public ClientDiceMessage(String username, int rolled){
        this.rolled = rolled;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getRolled() {
        return rolled;
    }
}
