package com.example.settlersofcatan.game;

/**
 * Abstract class for representing building that can be placed on a node, always has a player which
 * it belongs to.
 */
public abstract class NodePlaceable {

    protected Player player;

    public abstract void giveResource(Resource resource);
}
