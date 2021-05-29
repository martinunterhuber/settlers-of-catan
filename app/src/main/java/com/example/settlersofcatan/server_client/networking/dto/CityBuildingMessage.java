package com.example.settlersofcatan.server_client.networking.dto;

import com.example.settlersofcatan.game.Direction;
import com.example.settlersofcatan.game.TileCoordinates;

public class CityBuildingMessage extends BuildingMessage {
    public CityBuildingMessage() {
    }

    public CityBuildingMessage(int playerId, TileCoordinates tileCoordinates, Direction nodeDirection) {
        super(playerId, tileCoordinates, nodeDirection);
    }
}
