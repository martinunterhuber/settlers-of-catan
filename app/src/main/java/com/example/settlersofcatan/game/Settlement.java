package com.example.settlersofcatan.game;

/**
 * Class to represent a Settlement, extends NodePlaceable
 * Implements the giveResource method to give a copies of the resource to the owning player.
 */
public class Settlement extends Placeable implements NodePlaceable {
    private Node location;

    public Settlement(Player player) {
        super(player);
    }

    public Settlement(Player player, Node location) {
        super(player);
        this.location = location;
    }

    public Node getLocation() {
        return location;
    }


    @Override
    public void giveResource(Resource resource) {
        player.giveSingleResource(resource);
    }
}
