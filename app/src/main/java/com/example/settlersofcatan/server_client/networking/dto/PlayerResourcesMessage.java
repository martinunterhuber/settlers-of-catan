package com.example.settlersofcatan.server_client.networking.dto;

import androidx.annotation.NonNull;

import com.example.settlersofcatan.game.resources.PlayerResources;

public class PlayerResourcesMessage implements BaseMessage{
    public PlayerResources playerResources;
    public int cheaterId;

    public PlayerResourcesMessage() {
    }

    public PlayerResourcesMessage(PlayerResources playerResources) {
        this.playerResources = playerResources;
        this.cheaterId = -1;
    }

    public PlayerResourcesMessage(PlayerResources playerResources, int cheaterId) {
        this.playerResources = playerResources;
        this.cheaterId = cheaterId;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
