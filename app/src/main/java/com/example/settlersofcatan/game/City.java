package com.example.settlersofcatan.game;

/**
 * City building class, can be placed on a node and thus extends NodePlaceable.
 * Implements the giveResource method to give two copies of the resource to the owning player.
 */
public class City extends NodePlaceable {

    public City() {
        super(null, null);
    }

    public City(Player player, Node location) {
        super(player, location);
    }

    @Override
    public void giveResource(Resource resource) {
        player.giveSingleResource(resource);
        player.giveSingleResource(resource);
    }
}
