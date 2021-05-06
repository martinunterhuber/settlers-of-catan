package com.example.settlersofcatan.game;

/**
 * Class to represent a Settlement, extends NodePlaceable
 * Implements the giveResource method to give a copies of the resource to the owning player.
 */
public class Settlement extends NodePlaceable {
    public Settlement(Player player) {
        super(player);
    }

    @Override
    public void giveResource(Resource resource) {
        player.giveSingleResource(resource);
    }
}
