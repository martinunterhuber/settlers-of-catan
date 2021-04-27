package com.example.settlersofcatan.game;

/**
 * Class to represent a Settlement, extends NodePlaceable
 * Implements the giveResource method to give a copies of the resource to the owning player.
 */
public class Settlement extends NodePlaceable {

    @Override
    public void giveResource(Resource resource) {
        player.getResource(resource);
    }
}
