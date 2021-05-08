package com.example.settlersofcatan.game;

/**
 * Class to represent a Settlement, extends NodePlaceable
 * Implements the giveResource method to give a copies of the resource to the owning player.
 */

public class Settlement extends NodePlaceable {
    public Settlement(){
        super(null, null);
    }

    public Settlement(Player player, Node location) {
        super(player, location);
    }

    @Override
    public void giveResource(Resource resource) {
        player.giveSingleResource(resource);
    }
}
