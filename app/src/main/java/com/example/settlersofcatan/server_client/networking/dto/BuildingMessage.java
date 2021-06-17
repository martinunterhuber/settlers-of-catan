package com.example.settlersofcatan.server_client.networking.dto;

import com.example.settlersofcatan.game.board.Direction;
import com.example.settlersofcatan.game.board.TileCoordinates;

public abstract class BuildingMessage extends BaseMessage {
    public int playerId;
    public TileCoordinates tileCoordinates;
    public Direction direction;

    public BuildingMessage() {
    }

    public BuildingMessage(int playerId, TileCoordinates tileCoordinates, Direction direction) {
        this.playerId = playerId;
        this.tileCoordinates = tileCoordinates;
        this.direction = direction;
    }
}
