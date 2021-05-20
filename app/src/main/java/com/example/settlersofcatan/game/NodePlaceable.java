package com.example.settlersofcatan.game;

public abstract class NodePlaceable extends Placeable {
    protected Node location;

    public NodePlaceable(Player player, Node location) {
        super(player);
        this.location = location;
    }

    public Node getLocation() {
        return location;
    }

    public abstract void giveResource(Resource resource);
}
