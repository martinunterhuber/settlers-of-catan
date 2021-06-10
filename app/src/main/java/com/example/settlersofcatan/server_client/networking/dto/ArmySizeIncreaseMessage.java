package com.example.settlersofcatan.server_client.networking.dto;

public class ArmySizeIncreaseMessage extends BaseMessage {
    public int playerId;

    public ArmySizeIncreaseMessage(){
    }

    public ArmySizeIncreaseMessage(int playerId){
        this.playerId = playerId;
    }
}
