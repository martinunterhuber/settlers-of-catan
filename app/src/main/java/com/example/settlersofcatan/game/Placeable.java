package com.example.settlersofcatan.game;

public abstract class Placeable {
    protected Player player;

    public Placeable(Player player) {
        this.player = player;
    }
}
