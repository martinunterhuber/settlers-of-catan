package com.example.settlersofcatan.server_client.networking.dto;

import com.example.settlersofcatan.game.board.Direction;
import com.example.settlersofcatan.game.board.TileCoordinates;

public class SettlementBuildingMessage extends BuildingMessage {
    public SettlementBuildingMessage() {
    }

    public SettlementBuildingMessage(int playerId, TileCoordinates tileCoordinates, Direction nodeDirection) {
        super(playerId, tileCoordinates, nodeDirection);
    }
}
