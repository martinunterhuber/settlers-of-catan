package com.example.settlersofcatan.server_client.networking.dto;

import com.example.settlersofcatan.game.PlayerColor;

public class ColorMessage implements BaseMessage{
    public int playerId;
    public PlayerColor playerColor;

    public ColorMessage() {
    }

    public ColorMessage(int playerId, PlayerColor playerColor) {
        this.playerId = playerId;
        this.playerColor = playerColor;
    }
}
