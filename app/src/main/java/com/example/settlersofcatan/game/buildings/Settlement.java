package com.example.settlersofcatan.game.buildings;

import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.board.Node;
import com.example.settlersofcatan.game.resources.Resource;
import com.example.settlersofcatan.game.resources.ResourceMap;

/**
 * Class to represent a Settlement, extends NodePlaceable
 * Implements the giveResource method to give a copies of the resource to the owning player.
 */

public class Settlement extends NodePlaceable {
    public static final ResourceMap costs = new ResourceMap(new int[]{1, 1, 1, 1, 0});

    private Settlement(){
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
