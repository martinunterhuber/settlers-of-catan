package com.example.settlersofcatan.server_client.networking.dto;

import com.example.settlersofcatan.game.board.TileCoordinates;

public class MovedRobberMessage implements BaseMessage {
    public TileCoordinates coordinates;

    public MovedRobberMessage(){
    }

    public MovedRobberMessage(TileCoordinates coordinates){
        this.coordinates = coordinates;
    }
}
