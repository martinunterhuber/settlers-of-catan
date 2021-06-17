package com.example.settlersofcatan.server_client.networking.dto;

import com.example.settlersofcatan.game.board.Direction;
import com.example.settlersofcatan.game.board.TileCoordinates;

public class RoadBuildingMessage extends BuildingMessage {
    public RoadBuildingMessage() {
    }

    public RoadBuildingMessage(int playerId, TileCoordinates tileCoordinates, Direction edgeDirection) {
        super(playerId, tileCoordinates, edgeDirection);
    }
}
