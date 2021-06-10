package com.example.settlersofcatan.server_client.networking.dto;

public class EndTurnMessage extends BaseMessage {
    public int turnCount;
    public int nextPlayerId;

    public EndTurnMessage(){
    }

    public EndTurnMessage(int turnCount, int nextPlayerId) {
        this.turnCount = turnCount;
        this.nextPlayerId = nextPlayerId;
    }
}
