package com.example.settlersofcatan.server_client.networking.dto;

import com.example.settlersofcatan.game.Game;

public class GameStateMessage implements BaseMessage {
    public Game game;

    public GameStateMessage(){

    }

    public GameStateMessage(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return game.toString();
    }
}
