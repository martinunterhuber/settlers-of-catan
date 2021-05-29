package com.example.settlersofcatan.server_client.networking.dto;

import com.example.settlersofcatan.game.TileCoordinates;

public class MovedRobberMessage extends BaseMessage {
    public TileCoordinates coordinates;

    public MovedRobberMessage(){
    }

    public MovedRobberMessage(TileCoordinates coordinates){
        this.coordinates = coordinates;
    }
}
