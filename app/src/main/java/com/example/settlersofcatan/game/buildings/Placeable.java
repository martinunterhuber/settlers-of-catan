package com.example.settlersofcatan.game.buildings;

import com.example.settlersofcatan.game.Player;

public abstract class Placeable {
    protected Player player;

    public Placeable(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPlayerId(){
        return player.getId();
    }
}
