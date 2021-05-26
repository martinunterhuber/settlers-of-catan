package com.example.settlersofcatan.server_client.networking.dto;

import com.example.settlersofcatan.PlayerResources;

import androidx.annotation.NonNull;

public class PlayerResourcesMessage extends BaseMessage{
    public PlayerResources playerResources;

    public PlayerResourcesMessage() {
    }

    public PlayerResourcesMessage(PlayerResources playerResources) {
        this.playerResources = playerResources;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
