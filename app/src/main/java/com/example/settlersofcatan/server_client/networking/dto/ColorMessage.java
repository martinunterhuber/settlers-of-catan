package com.example.settlersofcatan.server_client.networking.dto;

public class ColorMessage extends BaseMessage{
    public int playerId;
    public String playerColor;

    public ColorMessage() {
    }

    public ColorMessage(int playerId, String playerColor) {
        this.playerId = playerId;
        this.playerColor = playerColor;
    }


}
