package com.example.settlersofcatan.server_client.networking.dto;

import com.example.settlersofcatan.game.Direction;
import com.example.settlersofcatan.game.TileCoordinates;

public class SettlementConstructionMessage extends BaseMessage {
    public int playerId;
    public TileCoordinates tileCoordinates;
    public Direction nodeDirection;

    public SettlementConstructionMessage() {
    }

    public SettlementConstructionMessage(int playerId, TileCoordinates tileCoordinates, Direction nodeDirection) {
        this.playerId = playerId;
        this.tileCoordinates = tileCoordinates;
        this.nodeDirection = nodeDirection;
    }
}
