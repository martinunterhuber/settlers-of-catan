package com.example.settlersofcatan.server_client.networking.dto;

public class ArmySizeIncreaseMessage implements BaseMessage {
    public int playerId;

    public ArmySizeIncreaseMessage(){
    }

    public ArmySizeIncreaseMessage(int playerId){
        this.playerId = playerId;
    }
}
