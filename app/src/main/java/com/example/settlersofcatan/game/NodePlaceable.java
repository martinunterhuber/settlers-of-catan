package com.example.settlersofcatan.game;

public abstract class NodePlaceable extends Placeable {
    public NodePlaceable(Player player) {
        super(player);
    }

    public abstract void giveResource(Resource resource);
}
